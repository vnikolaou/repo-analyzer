package net.pionware.oss.repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.pionware.oss.repo.model.entity.RunItemEntity;

public interface RunItemRepository extends JpaRepository<RunItemEntity, Long> {
}
