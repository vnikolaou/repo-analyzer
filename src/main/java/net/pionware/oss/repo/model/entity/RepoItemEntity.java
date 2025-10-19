package net.pionware.oss.repo.model.entity;

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
public class RepoItemEntity {

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
    private RunItemEntity runItem;
    
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

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRepoId() {
		return repoId;
	}

	public void setRepoId(final String repoId) {
		this.repoId = repoId;
	}

	public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(final Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(final Integer stars) {
        this.stars = stars;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(final String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(final String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(final Integer watchers) {
        this.watchers = watchers;
    }

    public Boolean getHasIssues() {
        return hasIssues;
    }

    public void setHasIssues(final Boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(final Integer forks) {
        this.forks = forks;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(final Integer openIssues) {
        this.openIssues = openIssues;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(final String license) {
        this.license = license;
    }
    
    public RunItemEntity getRunItem() {
        return runItem;
    }

    public void setRunItem(final RunItemEntity runItem) {
        this.runItem = runItem;
    }

    
	public boolean isCloned() {
		return cloned;
	}

	public void setCloned(final boolean cloned) {
		this.cloned = cloned;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(final boolean chosen) {
		this.chosen = chosen;
	}

	public Integer getCommits() {
		return commits;
	}

	public void setCommits(final Integer commits) {
		this.commits = commits;
	}

	public Integer getContributors() {
		return contributors;
	}

	public void setContributors(final Integer contributors) {
		this.contributors = contributors;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Instant getPushedAt() {
		return pushedAt;
	}

	public void setPushedAt(final Instant pushedAt) {
		this.pushedAt = pushedAt;
	}

	public Boolean getAnalyzed() {
		return analyzed;
	}

	public void setAnalyzed(final Boolean analyzed) {
		this.analyzed = analyzed;
	}

	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(final BigDecimal coverage) {
		this.coverage = coverage;
	}

	public Boolean getFailed() {
		return failed;
	}

	public void setFailed(final Boolean failed) {
		this.failed = failed;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public String getCommitId() {
		return commitId;
	}
	
	public void setCommitId(final String commitId) {
		this.commitId = commitId;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(final Integer complexity) {
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
