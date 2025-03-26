package com.pionware.oss.github.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pionware.oss.github.entity.RepoItem;
import com.pionware.oss.github.model.Repo;
import com.pionware.oss.github.service.GitHubService;
import com.pionware.oss.github.service.RepoService;

import reactor.core.publisher.Flux;

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
    public ResponseEntity<List<Repo>> fetchRepos(
    		@RequestParam("id") Long id,
    		@RequestParam("q") String query) throws Exception {
    	List<Repo> results = gitHubService.fetchRepos(id, query);
   
        return ResponseEntity.ok(results);
    }
    
    @GetMapping
    public ResponseEntity<List<Repo>> getRepos(
    		@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getRepositories(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Repo> getRepo(
    		@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getRepository(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Repo> updateRepo(@PathVariable("id") Long id, @RequestBody Repo updatedRepo) {
        Repo updated = repoService.updateRepository(id, updatedRepo);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(value = "/sample")
    public ResponseEntity<Map<Integer, List<RepoItem>>> getSample(
    		@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(repoService.getSample(id));
    }

}
