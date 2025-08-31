package net.pionware.oss.repo.service;

import java.util.List;
import java.util.Map;

import net.pionware.oss.repo.model.entity.RepoItemEntity;
import net.pionware.oss.repo.model.entity.RunItemEntity;
import net.pionware.oss.repo.model.response.RepoResponse;

public interface RepoService {
	public int saveRepositories(RunItemEntity runItem, List<RepoResponse> repos);

	public List<RepoResponse> getRepositories(Long runId);
	public void deleteRepositories(RunItemEntity runItem);
	
	public RepoResponse getRepository(Long id);
	public RepoResponse updateRepository(Long id, RepoResponse repo);
	
	public Map<Integer, List<RepoItemEntity>> getSample(Long runId);
}
