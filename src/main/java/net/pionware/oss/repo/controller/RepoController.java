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

@RestController
@RequestMapping("/api/repos")
public class RepoController {

    private final GitHubService gitHubService;
    private final RepoService repoService;
    
    public RepoController(final GitHubService gitHubService, final RepoService repoService) {
        this.gitHubService = gitHubService;
        this.repoService = repoService;
    }

    @GetMapping("/total-results")
    public ResponseEntity<Optional<Integer>> getSearchTotalResults(final @RequestParam("q") String query) throws Exception {
        if (!StringUtils.hasText(query)) {
            throw new IllegalArgumentException("Query parameter 'q' must not be null or empty");
        }
        return ResponseEntity.ok(gitHubService.getSearchTotalResults(query));
    }
    
    @GetMapping("/fetch-repos")
    public ResponseEntity<List<RepoResponse>> fetchRepos(
    		final @RequestParam("id") Long runItemId,
    		final @RequestParam("q") String query) throws Exception {
    	final List<RepoResponse> results = gitHubService.fetchRepos(runItemId, query);
   
        return ResponseEntity.ok(results);
    }
    
    @GetMapping
    public ResponseEntity<List<RepoResponse>> getRepos(
    		final @RequestParam("id") Long runItemId) throws Exception {
        return ResponseEntity.ok(repoService.getRepositories(runItemId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RepoResponse> getRepo(
    		final @PathVariable("id") Long repoItemId) throws Exception {
        return ResponseEntity.ok(repoService.getRepository(repoItemId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RepoResponse> updateRepo(final @PathVariable("id") Long repoItemId, 
    		final @RequestBody RepoResponse updatedRepo) {
        final RepoResponse updated = repoService.updateRepository(repoItemId, updatedRepo);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/sample")
    public ResponseEntity<Map<Integer, List<RepoItemEntity>>> getSample(
    		final @RequestParam("id") Long runItemId) throws Exception {
        return ResponseEntity.ok(repoService.getSample(runItemId));
    }

}
