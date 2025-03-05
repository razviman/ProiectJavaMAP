package a4.repository;

public class RepoException extends Exception {
    public RepoException(String message) {
        super(message);
    }

    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }
}