package net.pionware.oss.repo.exception;

public class GitHubErrorException extends RuntimeException {
    private static final long serialVersionUID = -6609201573429511249L;

	public GitHubErrorException(final String message) {
        super(message);
    }

    public GitHubErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}