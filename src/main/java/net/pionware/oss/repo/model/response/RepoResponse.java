package net.pionware.oss.repo.model.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RepoResponse(
		@JsonProperty("id") Long id, 
		@JsonProperty("repoId") String repoId, 
		@JsonProperty("isPrivate") boolean isPrivate, 
		@JsonProperty("fullName") String fullName, 
		@JsonProperty("stars") Integer stars, 
		@JsonProperty("sshUrl") String sshUrl, 
		@JsonProperty("cloneUrl") String cloneUrl, 
		@JsonProperty("size") Integer size, 
		@JsonProperty("watchers") Integer watchers, 
		@JsonProperty("hasIssues") boolean hasIssues, 
		@JsonProperty("forks") Integer forks, 
		@JsonProperty("openIssues") Integer openIssues, 
		@JsonProperty("license") String license, 
		@JsonProperty("cloned") boolean cloned, 
		@JsonProperty("chosen") boolean chosen, 
		@JsonProperty("commits") Integer commits, 
		@JsonProperty("contributors") Integer contributors, 
		@JsonProperty("createdAt") 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
		Instant createdAt, 
		@JsonProperty("updatedAt")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
		Instant updatedAt,
		@JsonProperty("pushedAt") 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
		Instant pushedAt, 
		@JsonProperty("analyzed") Boolean analyzed, 
		@JsonProperty("coverage") BigDecimal coverage,
		@JsonProperty("failed") boolean failed,
		@JsonProperty("comments") String comments,
		@JsonProperty("commitId") String commitId,
		@JsonProperty("complexity") Integer complexity
		) {
	
}