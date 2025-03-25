package com.pionware.oss.github.util;

public class GitHubErrorException extends RuntimeException {
    private static final long serialVersionUID = -6609201573429511249L;

	public GitHubErrorException(String message) {
        super(message);
    }

    public GitHubErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}