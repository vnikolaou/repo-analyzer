package net.pionware.oss.repo.service;

import java.util.List;
import java.util.Optional;

import net.pionware.oss.repo.model.response.RepoResponse;

public interface GitHubService {
	Optional<Integer> getSearchTotalResults(String query) throws Exception;
	List<RepoResponse> fetchRepos(Long runItemId, String query) throws Exception;
}
