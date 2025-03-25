package com.pionware.oss.github.entity;

import java.math.BigDecimal;
import java.time.Instant;

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
@Table(name = "repository", schema = "public")
public class RepoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repository_id_seq")
    @SequenceGenerator(name = "repository_id_seq", sequenceName = "repository_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "repo_id")
    private String repoId;
    
    @Column(name = "private")
    private Boolean isPrivate;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "ssh_url")
    private String sshUrl;

    @Column(name = "clone_url")
    private String cloneUrl;

    @Column(name = "size")
    private Integer size;

    @Column(name = "watchers")
    private Integer watchers;

    @Column(name = "has_issues")
    private Boolean hasIssues;

    @Column(name = "forks")
    private Integer forks;

    @Column(name = "open_issues")
    private Integer openIssues;

    @Column(name = "license")
    private String license;

    @ManyToOne
    @JoinColumn(name = "run_item_id", nullable = false)
    private RunItem runItem;
    
    @Column
    private boolean cloned;

    @Column
    private boolean chosen;

    @Column
    private Integer commits;

    @Column
    private Integer contributors;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private Instant pushedAt;

    @Column
    private boolean analyzed;

    @Column(precision = 5, scale = 2)
    private BigDecimal coverage;
    
    @Column
    private boolean failed;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "commite_id")
    private String commitId;
    
    @Column(name = "complexity")
    private Integer complexity;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepoId() {
		return repoId;
	}

	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}

	public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public Boolean getHasIssues() {
        return hasIssues;
    }

    public void setHasIssues(Boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(Integer openIssues) {
        this.openIssues = openIssues;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
    
    public RunItem getRunItem() {
        return runItem;
    }

    public void setRunItem(RunItem runItem) {
        this.runItem = runItem;
    }

    
	public boolean isCloned() {
		return cloned;
	}

	public void setCloned(boolean cloned) {
		this.cloned = cloned;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public Integer getCommits() {
		return commits;
	}

	public void setCommits(Integer commits) {
		this.commits = commits;
	}

	public Integer getContributors() {
		return contributors;
	}

	public void setContributors(Integer contributors) {
		this.contributors = contributors;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Instant getPushedAt() {
		return pushedAt;
	}

	public void setPushedAt(Instant pushedAt) {
		this.pushedAt = pushedAt;
	}

	public Boolean getAnalyzed() {
		return analyzed;
	}

	public void setAnalyzed(Boolean analyzed) {
		this.analyzed = analyzed;
	}

	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}

	public Boolean getFailed() {
		return failed;
	}

	public void setFailed(Boolean failed) {
		this.failed = failed;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCommitId() {
		return commitId;
	}
	
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}

	@Override
	public String toString() {
		return "RepoItem [id=" + id + ", repoId=" + repoId + ", isPrivate=" + isPrivate + ", fullName=" + fullName
				+ ", stars=" + stars + ", sshUrl=" + sshUrl + ", cloneUrl=" + cloneUrl + ", size=" + size
				+ ", watchers=" + watchers + ", hasIssues=" + hasIssues + ", forks=" + forks + ", openIssues="
				+ openIssues + ", license=" + license + ", runItem=" + runItem + ", cloned=" + cloned + ", chosen="
				+ chosen + ", commits=" + commits + ", contributors=" + contributors + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", pushedAt=" + pushedAt + ", analyzed=" + analyzed + ", coverage="
				+ coverage + ", failed=" + failed + ", comments=" + comments + ", commitId=" + commitId
				+ ", complexity=" + complexity + "]";
	}


	
}
