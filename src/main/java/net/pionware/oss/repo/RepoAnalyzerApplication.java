package net.pionware.oss.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Repo Analyzer Spring Boot application.
 *
 * <p>This class bootstraps the application by invoking
 * {@link SpringApplication#run(Class, String...)} in its
 * {@code main} method. It is declared {@code final} to prevent
 * subclassing, and its private constructor ensures it cannot
 * be instantiated.</p>
 */
@SpringBootApplication
public final class RepoAnalyzerApplication {

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * <p>The application is started via the {@link #main(String[])} method
     * instead of creating an instance of this class.</p>
     */
    private RepoAnalyzerApplication() { }

    /**
     * Main entry point of the Repo Analyzer application.
     *
     * <p>Initializes and launches the Spring Boot application context.</p>
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(RepoAnalyzerApplication.class, args);
    }
}
