package net.pionware.oss.repo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.pionware.oss.repo.model.entity.RepoItemEntity;
import net.pionware.oss.repo.model.response.RepoResponse;
import net.pionware.oss.repo.service.GitHubService;
import net.pionware.oss.repo.service.RepoService;

/**
 * REST controller for managing repositories.
 *
 * <p>Exposes operations to create, retrieve, update, and manage repository-related resources 
 * within the application.</p>
 * 
 * @author Vangelis Nikolaou
 * @since 1.0.0
 * @see net.pionware.oss.repo.service.RepoService
 * @see net.pionware.oss.repo.service.GitHubService
 */
@RestController
@RequestMapping("/api/repos")
public class RepoController {

	/**
	 * Service component responsible for interactions with the GitHub API.
	 */
    private final GitHubService gitHubService;
    
    /**
     * Service component that provides business logic for managing 
     * {@link ResponseEntity} instances.
     */
    private final RepoService repoService;
    
    /**
     * Creates a new {@code RepoController} with the specified service components.
     *
     * <p>This constructor is typically used for dependency injection.</p>
     *
     * @param gitHubService the {@link GitHubService} service responsible for GitHub API operations
     * @param repoService the {@link RepoService} service used to manage local repository entities and
     *                    related business logic
     */
    public RepoController(final GitHubService gitHubService, final RepoService repoService) {
        this.gitHubService = gitHubService;
        this.repoService = repoService;
    }

    /**
     * Retrieves the total number of GitHub search results for the specified query.
     *
     * <p>Invoked via an HTTP GET request, delegates to the {@link GitHubService} to perform a GitHub API
     * search and return the total number of matching repositories.</p>
     *  
     * @param query the search query used to fetch repository results from GitHub
     * @return a {@link ResponseEntity} containing an {@link Optional} with the total
     *         number of search results and an HTTP 200 (OK) status
     * @throws IllegalArgumentException if the query parameter is missing or blank
     */
    @GetMapping("/total-results")
    public ResponseEntity<Optional<Integer>> getSearchTotalResults(final @RequestParam("q") String query) {
        if (!StringUtils.hasText(query)) {
            throw new IllegalArgumentException("Query parameter 'q' must not be null or empty");
        }
        return ResponseEntity.ok(gitHubService.getSearchTotalResults(query));
    }
    
    /**
     * Fetches repositories from GitHub based on the provided search query and run item ID.
     *
     * <p>Invoked via an HTTP GET request, delegates to the {@link GitHubService} to execute a remote search
     * and return a list of matching repositories as {@link RepoResponse} objects.
     * If the operation fails, a 500 (Internal Server Error) response is returned through a global handler 
     * that catches a {@link RuntimeException} exception thrown by the {@link GitHubService}.</p>
     *
     * @param runItemId the ID of the associated run item; provided as a query parameter
     * @param query the search query string used to fetch repositories from GitHub
     * @return a {@link ResponseEntity} containing a list of {@link RepoResponse}
     *         objects and an HTTP 200 (OK) status; If fails a 500 (Internal Server Error) 
     *         response is returned
     */
    @GetMapping("/fetch-repos")
    public ResponseEntity<List<RepoResponse>> fetchRepos(
    		final @RequestParam("id") Long runItemId,
    		final @RequestParam("q") String query) {
    	final List<RepoResponse> results = gitHubService.fetchRepos(runItemId, query);
   
        return ResponseEntity.ok(results);
    }
    
    /**
     * Retrieves all repositories associated with the specified run item.
     *
     * <p>Invoked via an HTTP GET request, delegates to the {@link RepoService} 
     * to retrieve all {@link RepoResponse} records linked to the given run item. 
     * If no repositories are found, an empty list is returned.</p>
     *
     * @param runItemId the unique identifier of the run item whose repositories
     *                  are to be retrieved;
     * @return a {@link ResponseEntity} containing a list of {@link RepoResponse}
     *         objects and an HTTP 200 (OK) status
     */
    @GetMapping
    public ResponseEntity<List<RepoResponse>> getRepos(
    		final @RequestParam("id") Long runItemId) {
        return ResponseEntity.ok(repoService.getRepositories(runItemId));
    }
    
    /**
     * Retrieves a specific repository by its unique identifier.
     *
     * <p>Invoked via an HTTP GET request, delegates to the {@link RepoService} 
     * to fetch repository details. If the repository does not exist, a 500 (Internal Server Error) 
     * response is returned through a global handler that catches a {@link RuntimeException} exception 
     * thrown by the {@link RepoService}.</p>
     *
     * @param repoItemId the unique identifier of the repository to retrieve;
     * @return a {@link ResponseEntity} containing the requested {@link RepoResponse}
     *         and an HTTP 200 (OK) status if found; otherwise a 500 (Internal Server Error) 
     *         response is returned
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepoResponse> getRepo(
    		final @PathVariable("id") Long repoItemId) {
        return ResponseEntity.ok(repoService.getRepository(repoItemId));
    }
    
    /**
     * Updates an existing repository record identified by its unique ID.
     *
     * <p>Invoked via an HTTP PUT request with the updated {@link RepoResponse} data 
     * in the request body. The method delegates to perform the update. If the operation fails for
     * any reason, a 500 (Internal Server Error) response is returned through a global handler 
     * that catches a {@link RuntimeException} exception thrown by the {@link RepoService}.</p>
     *
     * @param repoItemId the unique identifier of the repository to update
     * @param updatedRepo the updated {@link RepoResponse} data provided in the request body;
     * @return a {@link ResponseEntity} containing the updated {@link RepoResponse}
     *         and an HTTP 200 (OK) status if the update succeeds;
     *         an HTTP 500 (Internal Server Error) response is returned if the update fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepoResponse> updateRepo(final @PathVariable("id") Long repoItemId, 
    		final @RequestBody RepoResponse updatedRepo) {
        final RepoResponse updated = repoService.updateRepository(repoItemId, updatedRepo);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a sample subset of repositories associated with the specified run item.
     *
     * <p>Invoked via an HTTP GET request to <code>/api/repos/sample</code>, delegates to 
     * the {@link RepoService} to retrieve a map of sampled grouped repositories. 
     * If no repositories are available, an empty map is returned.</p>
     *
     * @param runItemId the unique identifier of the run item whose sample repositories
     *                  are to be retrieved
     * @return a {@link ResponseEntity} containing a map of grouped repository samples 
     *         and an HTTP 200 (OK) status
     */
    @GetMapping("/sample")
    public ResponseEntity<Map<Integer, List<RepoItemEntity>>> getSample(
    		final @RequestParam("id") Long runItemId) {
        return ResponseEntity.ok(repoService.getSample(runItemId));
    }

}
