package com.pionware.oss.github.service;

import java.util.List;
import java.util.Optional;

import com.pionware.oss.github.model.Config;
import com.pionware.oss.github.model.Repo;

import reactor.core.publisher.Flux;

public interface GitHubService {
	public Optional<Integer> getSearchTotalResults(String query) throws Exception;
	public List<Repo> fetchRepos(Long id, String query) throws Exception;
	
	public void cloneRepository(String cloneUrl, String cloneDirectoryPath);
	
	public void cloneRepositoriesAsync(List<String> repoUrls);
	public Flux<String> getCloneProgressStream();
}
