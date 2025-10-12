package net.pionware.oss.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RepoAnalyzerApplication {

	private RepoAnalyzerApplication() { }
	
	public static void main(final String[] args) {
		SpringApplication.run(RepoAnalyzerApplication.class, args);
	}

}
