package net.pionware.oss.repo.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.pionware.oss.repo.exception.GitHubErrorException;
import net.pionware.oss.repo.model.error.ServerError;

@ControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * This type of exception contains a message (string) in json format.
	 * Essentially this can be forwarded "as is" to frontend which should be able to
	 * handle the json effectively
	 */
    @ExceptionHandler(GitHubErrorException.class)
    public ResponseEntity<String> handleServerErrorException(GitHubErrorException ex) {
    	
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /*
     * This type of exception (any type) is converted to json so as
     * the frontend can handle all the exceptions almost in a similar way 
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleException(Exception ex) {
    	String json = "{\"status\":500,\"message\":\"Internal Server Error\"}";
    	
        try {
        	ServerError error = new ServerError(500, ex.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(error);
        } catch (JsonProcessingException e) {
            
        }
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
