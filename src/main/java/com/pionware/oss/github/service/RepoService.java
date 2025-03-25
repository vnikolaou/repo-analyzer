package com.pionware.oss.github.service;

import java.util.List;
import java.util.Map;

import com.pionware.oss.github.entity.RepoItem;
import com.pionware.oss.github.entity.RunItem;
import com.pionware.oss.github.model.Repo;

public interface RepoService {
	public int saveRepositories(RunItem runItem, List<Repo> repos);

	public List<Repo> getRepositories(Long runId);
	public void deleteRepositories(RunItem runItem);
	
	public Repo getRepository(Long id);
	public Repo updateRepository(Long id, Repo repo);
	
	public Map<Integer, List<RepoItem>> getSample(Long runId);
}
