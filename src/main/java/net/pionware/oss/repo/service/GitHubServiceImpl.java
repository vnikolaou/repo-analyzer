package net.pionware.oss.repo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.pionware.oss.repo.exception.GitHubErrorException;
import net.pionware.oss.repo.model.entity.RunItemEntity;
import net.pionware.oss.repo.model.response.RepoResponse;
import net.pionware.oss.repo.service.internal.ResponseHandler;

@Service
final class GitHubServiceImpl implements GitHubService {
	private static final Logger LOGGER = LogManager.getLogger(GitHubServiceImpl.class);

	private static final String REPO_API_URL = "https://api.github.com/search/repositories";
	
    private final SettingService settingService;
    private final RepoService repoService;
    private final RunService runService;
    
    public GitHubServiceImpl(final SettingService settingService, final RepoService repoService, final RunService runService) { 
    	this.settingService = settingService;
    	this.repoService = repoService;
    	this.runService = runService;
    }

    public Optional<Integer> getSearchTotalResults(final String query) {
        return Optional.ofNullable(executeApiCall(query, null, null, this::parseTotalResultsResponse));
    }
    
    public List<RepoResponse> fetchRepos(final Long runItemId, final String query) {
        int page = 1;
        int resultsPerPage = 30; // GitHub API default per page

        List<RepoResponse> repos = new ArrayList<>();
        List<RepoResponse> results = null;
        do {
            String paginatedQuery = query; // + "&page=" + page + "&per_page=" + resultsPerPage;
            results = executeApiCall(paginatedQuery, page, resultsPerPage, this::parseFetchRepositoriesResponse);

            if(results != null) {
            	repos.addAll(results);
            }
     
            page++;    
      
        } while (!(results == null || results.isEmpty())); 
        
        RunItemEntity runItem = runService.getRunItemById(runItemId);
    
        this.repoService.saveRepositories(runItem, repos);
        
        return repos;
    }
    
	private Integer parseTotalResultsResponse(final String jsonResponse) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode root = objectMapper.readTree(jsonResponse);
		JsonNode totalNode = root.path("total_count");

		if(totalNode.canConvertToInt()) {
			return totalNode.asInt();
		}    
		
		return null;
    }
	
    private List<RepoResponse> parseFetchRepositoriesResponse(final String jsonResponse) throws IOException{
    	final ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule());
    	
    	final JsonNode root = objectMapper.readTree(jsonResponse);
    	final JsonNode items = root.path("items");

    	final List<RepoResponse> repos = items.isEmpty() ? null : new ArrayList<>();
   
    	// construct the list of Repository objects and print the list in dev mode
    	for (JsonNode item : items) {
    		final String repoId = item.path("id").asText();
    		final boolean isPrivate = item.path("private").asBoolean();
    		final String fullName = item.path("full_name").asText();
    		final Integer stars = item.path("stargazers_count").asInt();
    		final String sshUrl = item.path("ssh_url").asText();
    		final String cloneUrl = item.path("clone_url").asText();
    		final Integer size = item.path("size").asInt();
    		final Integer watchers = item.path("watchers_count").asInt();
    		final boolean hasIssues = item.path("has_issues").asBoolean();
    		final Integer forks = item.path("forks_count").asInt();
    		final Integer openIssues = item.path("open_issues_count").asInt();
    		final String license = item.path("license").path("key").asText();
    		final Instant createdAt = Instant.parse(item.path("created_at").asText());
    		final Instant updatedAt =  Instant.parse(item.path("updated_at").asText());
    		final Instant pushedAt =  Instant.parse(item.path("pushed_at").asText());
    		
    		final RepoResponse repo = new RepoResponse(null, repoId, isPrivate, fullName, stars, 
    				sshUrl, cloneUrl, size, watchers, hasIssues, forks, openIssues, license,
    				false, false, null, null, createdAt, updatedAt, pushedAt, false, null, false, null, null, null);

    		repos.add(repo);
    	}

    	if(repos != null) {
    		if(LOGGER.isDebugEnabled()) {
    			LOGGER.debug("Received response:\n {}", objectMapper.writeValueAsString(repos));
    		}
    	}
  
    	return repos;
    }
  
    private <T> T executeApiCall(final String query, final Integer page, final Integer resultsPerPage, final ResponseHandler<T> handler) {
        HttpURLConnection connection = null;

        try {
        	final String token = settingService.getSettingByKey("API_TOKEN").getValue();
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            if(page != null && resultsPerPage != null) {
            	encodedQuery+="&page=" + page + "&per_page=" + resultsPerPage;
            }
            final URL url = new URL(REPO_API_URL + "?q=" + encodedQuery);
   
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            setRequestHeaders(connection, token);

            final int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
            	final String response = parseResponseStream(connection);
                return handler.handle(response);
            } else {
            	final String errorResponse = parseErrorStream(connection);
                throw new GitHubErrorException(errorResponse);
            }
		} catch (Exception e) {
			throw new RuntimeException("Error during API call: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    private String parseResponseStream(final HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream()) {
            return readStream(inputStream);
        }
    }

    private String parseErrorStream(final HttpURLConnection connection) throws IOException {
        try (InputStream errorStream = connection.getErrorStream()) {
            return readStream(errorStream);
        }
    }
    
    private String readStream(final InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        	final StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
    
    private void setRequestHeaders(final HttpURLConnection connection, final String token) {
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
    }
}
