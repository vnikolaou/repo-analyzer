package com.pionware.oss.github.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "run_item", schema = "public")
public class RunItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "run_item_id_seq")
    @SequenceGenerator(name = "run_item_id_seq", sequenceName = "run_item_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Column(name = "search_query", nullable = false)
    private String searchQuery;

    @Column(name = "total_results", nullable = false)
    private long totalResults;

    @Column(name = "max_limit", nullable = false)
    private long maxLimit;

    @ManyToOne
    @JoinColumn(name = "algorithm", nullable = false)
    private Algorithm algorithm;

    @Column(name = "status", nullable = false)
    private String status;
    
    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	// toString
	@Override
	public String toString() {
		return "RunItem [id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", searchQuery=" + searchQuery
				+ ", totalResults=" + totalResults + ", maxLimit=" + maxLimit + ", algorithm=" + algorithm + ", status="
				+ status + "]";
	}
}