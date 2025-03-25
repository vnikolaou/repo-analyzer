package com.pionware.oss.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pionware.oss.github.entity.RunItem;

public interface RunItemRepository extends JpaRepository<RunItem, Long> {
}
