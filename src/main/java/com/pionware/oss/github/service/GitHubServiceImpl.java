package com.pionware.oss.github.service;

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
import com.pionware.oss.github.entity.RunItem;
import com.pionware.oss.github.model.Repo;
import com.pionware.oss.github.util.GitHubErrorException;
import com.pionware.oss.github.util.ResponseHandler;

@Service
final class GitHubServiceImpl implements GitHubService {
	private static final Logger logger = LogManager.getLogger(GitHubServiceImpl.class);

	private static final String REPO_API_URL = "https://api.github.com/search/repositories";
	
    private SettingService settingService;
    private RepoService repoService;
    private RunService runService;
    
    public GitHubServiceImpl(SettingService settingService, RepoService repoService, RunService runService) { 
    	this.settingService = settingService;
    	this.repoService = repoService;
    	this.runService = runService;
    }

    public Optional<Integer> getSearchTotalResults(String query) throws Exception {
        return Optional.ofNullable(executeApiCall(query, null, null, this::parseTotalResultsResponse));
    }
    
    public List<Repo> fetchRepos(Long id, String query) throws Exception {
        int page = 1;
        int resultsPerPage = 30; // GitHub API default per page

        List<Repo> repos = new ArrayList<>();
        List<Repo> results = null;
        do {
            String paginatedQuery = query; // + "&page=" + page + "&per_page=" + resultsPerPage;
            results = executeApiCall(paginatedQuery, page, resultsPerPage, this::parseFetchRepositoriesResponse);

            if(results != null) {
            	repos.addAll(results);
            }
     
            page++;    
      
        } while (!(results == null || results.isEmpty())); 
        
        RunItem runItem = runService.getRunItemById(id);
    
        this.repoService.saveRepositories(runItem, repos);
        
        return repos;
    }
    
	private Integer parseTotalResultsResponse(String jsonResponse) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode root = objectMapper.readTree(jsonResponse);
		JsonNode totalNode = root.path("total_count");

		if(totalNode.canConvertToInt()) {
			return totalNode.asInt();
		}    
		
		return null;
    }
	
    private List<Repo> parseFetchRepositoriesResponse(String jsonResponse) throws IOException{
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule());
    	
    	JsonNode root = objectMapper.readTree(jsonResponse);
    	JsonNode items = root.path("items");

    	List<Repo> repos = items.isEmpty() ? null : new ArrayList<>();
   
    	// construct the list of Repository objects and print the list in dev mode
    	for (JsonNode item : items) {
    		String repoId = item.path("id").asText();
    		boolean isPrivate = item.path("private").asBoolean();
    		String fullName = item.path("full_name").asText();
    		Integer stars = item.path("stargazers_count").asInt();
    		String sshUrl = item.path("ssh_url").asText();
    		String cloneUrl = item.path("clone_url").asText();
    		Integer size = item.path("size").asInt();
    		Integer watchers = item.path("watchers_count").asInt();
    		boolean hasIssues = item.path("has_issues").asBoolean();
    		Integer forks = item.path("forks_count").asInt();
    		Integer openIssues = item.path("open_issues_count").asInt();
    		String license = item.path("license").path("key").asText();
    		Instant created_at = Instant.parse(item.path("created_at").asText());
    		Instant updated_at =  Instant.parse(item.path("updated_at").asText());
    		Instant pushed_at =  Instant.parse(item.path("pushed_at").asText());
    		
    		Repo repo = new Repo(null, repoId, isPrivate, fullName, stars, 
    				sshUrl, cloneUrl, size, watchers, hasIssues, forks, openIssues, license,
    				false, false, null, null, created_at, updated_at, pushed_at, false, null, false, null, null, null);

    		repos.add(repo);
    	}

    	if(repos != null) {
    		logger.debug("Received response:\n {}", objectMapper.writeValueAsString(repos));
    	}
  
    	return repos;
    }
  
    private <T> T executeApiCall(String query, Integer page, Integer resultsPerPage, ResponseHandler<T> handler) throws Exception {
        HttpURLConnection connection = null;

        try {
            String token = settingService.getSettingByKey("API_TOKEN").getValue();
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            if(page != null && resultsPerPage != null) {
            	encodedQuery+="&page=" + page + "&per_page=" + resultsPerPage;
            }
            URL url = new URL(REPO_API_URL + "?q=" + encodedQuery);
   
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            setRequestHeaders(connection, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String response = parseResponseStream(connection);
                return handler.handle(response);
            } else {
                String errorResponse = parseErrorStream(connection);
                throw new GitHubErrorException(errorResponse);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    private String parseResponseStream(HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream()) {
            return readStream(inputStream);
        }
    }

    private String parseErrorStream(HttpURLConnection connection) throws IOException {
        try (InputStream errorStream = connection.getErrorStream()) {
            return readStream(errorStream);
        }
    }
    
    private String readStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
    
    private void setRequestHeaders(HttpURLConnection connection, String token) {
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
    }
}
