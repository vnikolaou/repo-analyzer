package com.pionware.oss.github.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pionware.oss.github.entity.RepoItem;

public interface RepoItemRepository extends JpaRepository<RepoItem, Long> {
	public List<RepoItem> findByRunItemIdOrderByCreatedAtAsc(Long runId);
	
	public void deleteByRunItemId(Long runId);
}
