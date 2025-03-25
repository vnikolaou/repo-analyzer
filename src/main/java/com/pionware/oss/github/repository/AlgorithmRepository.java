package com.pionware.oss.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pionware.oss.github.entity.Algorithm;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
