package com.pionware.oss.github.service;

import java.util.List;
import java.util.Optional;

import com.pionware.oss.github.model.Repo;

public interface GitHubService {
	public Optional<Integer> getSearchTotalResults(String query) throws Exception;
	public List<Repo> fetchRepos(Long id, String query) throws Exception;
}
