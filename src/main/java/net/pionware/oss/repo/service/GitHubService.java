package net.pionware.oss.repo.service;

import java.util.List;
import java.util.Optional;

import net.pionware.oss.repo.model.response.RepoResponse;

public interface GitHubService {
	public Optional<Integer> getSearchTotalResults(String query) throws Exception;
	public List<RepoResponse> fetchRepos(Long id, String query) throws Exception;
}
