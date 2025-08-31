package net.pionware.oss.repo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.pionware.oss.repo.model.entity.RepoItemEntity;

public interface RepoItemRepository extends JpaRepository<RepoItemEntity, Long> {
	public List<RepoItemEntity> findByRunItemIdOrderByCreatedAtAsc(Long runId);
	
	public void deleteByRunItemId(Long runId);
}
