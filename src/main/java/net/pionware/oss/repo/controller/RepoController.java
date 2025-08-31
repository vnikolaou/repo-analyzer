package net.pionware.oss.repo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
    
    public RepoController(GitHubService gitHubService, RepoService repoService) {
        this.gitHubService = gitHubService;
        this.repoService = repoService;
    }

    @GetMapping("/total-results")
    public ResponseEntity<Optional<Integer>> getSearchTotalResults(@RequestParam("q") String query) throws Exception {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query parameter 'q' must not be null or empty");
        }
        return ResponseEntity.ok(gitHubService.getSearchTotalResults(query));
    }
    
    @GetMapping(value = "/fetch-repos")
    public ResponseEntity<List<RepoResponse>> fetchRepos(
    		@RequestParam("id") Long id,
    		@RequestParam("q") String query) throws Exception {
    	List<RepoResponse> results = gitHubService.fetchRepos(id, query);
   
        return ResponseEntity.ok(results);
    }
    
    @GetMapping
    public ResponseEntity<List<RepoResponse>> getRepos(
    		@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getRepositories(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RepoResponse> getRepo(
    		@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getRepository(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RepoResponse> updateRepo(@PathVariable("id") Long id, @RequestBody RepoResponse updatedRepo) {
        RepoResponse updated = repoService.updateRepository(id, updatedRepo);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(value = "/sample")
    public ResponseEntity<Map<Integer, List<RepoItemEntity>>> getSample(
    		@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getSample(id));
    }

}
