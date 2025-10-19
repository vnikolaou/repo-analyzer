package net.pionware.oss.repo.model.error;

public class ServerError {
    private int status;
    private String message;

    public ServerError(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
