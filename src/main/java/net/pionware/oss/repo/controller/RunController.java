package net.pionware.oss.repo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pionware.oss.repo.model.entity.RunItemEntity;
import net.pionware.oss.repo.service.RunService;

/**
 * REST controller for managing run items.
 * 
 * <p>Provides endpoints for listing, finding, creating, updating, deleting and cloning run items.</p>
 * 
 * @author Vangelis Nikolaou
 * @since 1.0.0
 * @see net.pionware.oss.repo.service.RunService
 */
@RestController
@RequestMapping("/api/run-items")
public class RunController {
	/**
	 * Service component that provides business logic for managing
	 * {@link RunItemEntity} instances and related operations. 
	 */
    private final RunService runService;
    
    /**
     * Constructs a new {@code RunController} with the specified {@link RunService}.
     *
     * <p>This constructor is typically used for dependency injection.</p>
     *
     * @param runService the {@link RunService} instance for handling run-related operations
     */
	public RunController(final RunService runService) { 
		this.runService = runService;
	}

	/**
	 * Retrieves all existing {@link RunItemEntity} records.
	 *
	 * <p>This endpoint is typically invoked via an HTTP GET request and returns
	 * a list of all run items managed by the system. If no items are found,
	 * an empty list is returned.</p>
	 *
	 * @return a {@link ResponseEntity} containing a list of {@link RunItemEntity}
	 *         objects and an HTTP 200 (OK) status
	 */  
    @GetMapping
    public ResponseEntity<List<RunItemEntity>> getAllRunItems() {
        return ResponseEntity.ok(runService.getAllRunItems());
    }
    
    /**
     * Retrieves a {@link RunItemEntity} by its unique identifier.
     *
     * <p>This endpoint is typically invoked via an HTTP GET request using a path
     * variable representing the run item ID. If a matching entity is found,
     * it is returned with an HTTP 200 (OK) status. If no entity exists for the
     * given ID, a 500 (Internal Server Error) response is returned through a global handler 
     * that catches a {@link IllegalStateException} exception thrown by the {@link RunService}.</p>
     *
     * @param runItemId the unique identifier of the {@link RunItemEntity} to retrieve
     * @return a {@link ResponseEntity} containing the {@link RunItemEntity} if found; 
     * otherwise a 500 (Internal Server Error) response is returned
     */
	@GetMapping("/{id}")
	public ResponseEntity<RunItemEntity> getRunItemById(final @PathVariable Long runItemId) {
		return ResponseEntity.ok(runService.getRunItemById(runItemId));
	}

	/**
	 * Creates a new {@link RunItemEntity}.
	 *
	 * <p>This endpoint is typically invoked via an HTTP POST request with a JSON body
	 * representing the {@link RunItemEntity} to be created. The provided entity is 
	 * persisted using the {@link RunService}, and the saved entity is
	 * returned in the response.</p>
	 *
	 * @param runItem the {@link RunItemEntity} to create, provided in the request body;
	 * @return a {@link ResponseEntity} containing the created {@link RunItemEntity}
	 *         and an HTTP 200 (OK) status upon success
	 */
    @PostMapping
    public ResponseEntity<RunItemEntity> createRunItem(final @RequestBody RunItemEntity runItem) {
        return ResponseEntity.ok(runService.saveRunItem(runItem));
    }

    /**
     * Updates an existing {@link RunItemEntity} identified by its unique ID.
     *
     * <p>This endpoint is typically invoked via an HTTP PUT request containing the
     * updated {@link RunItemEntity} in the request body. If the specified entity
     * exists, it is updated and returned with an HTTP 200 (OK) status. If no
     * entity exists for the given ID, a 500 (Internal Server Error) response is returned 
     * through a global handler that catches a {@link IllegalStateException} exception 
     * thrown by the {@link RunService}.</p>
     *
     * @param runItemId the unique identifier of the {@link RunItemEntity} to update;
     *                  extracted from the request path
     * @param runItem the updated {@link RunItemEntity} data provided in the request body;
     *                must not be {@code null}
     * @return a {@link ResponseEntity} containing the updated {@link RunItemEntity}
     *         if found, or a 500 (Internal Server Error) response if the entity does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<RunItemEntity> updateRunItem(final @PathVariable Long runItemId, final @RequestBody RunItemEntity runItem) {
        return ResponseEntity.ok(runService.updateRunItem(runItem));
    }

    /**
     * Deletes a {@link RunItemEntity} identified by its unique ID.
     *
     * <p>This endpoint is typically invoked via an HTTP DELETE request using a path
     * variable representing the run item ID. If the deletion succeeds, an HTTP
     * 204 (No Content) status is returned. If the entity does not exist or the
     * deletion fails for any reason, an HTTP 500 (Internal Server Error) response is returned 
     * through a global handler that catches a {@link IllegalStateException} exception 
     * thrown by the {@link RunService}.</p>
     *
     * @param runItemId the unique identifier of the {@link RunItemEntity} to delete;
     * @return a {@link ResponseEntity} with HTTP 204 (No Content) if the deletion
     *         succeeds; an HTTP 500 (Internal Server Error) may be returned if
     *         the deletion fails
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRunItem(final @PathVariable Long runItemId) {
        runService.deleteRunItem(runItemId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Initializes the cloning process for an existing {@link RunItemEntity}.
     *
     * <p>This endpoint is typically invoked via an HTTP POST request with a JSON body
     * representing the source {@link RunItemEntity}. The method triggers a cloning
     * operation in the {@link RunService}, returning the newly created clone if
     * the operation succeeds.</p>
     *
     * <p>If the cloning process fails for any reason (e.g., invalid input), an
     * HTTP 500 (Internal Server Error) response is returned through a global handler 
     * that catches a {@link IllegalStateException} exception thrown by the {@link RunService}.</p>
     *
     * @param runItem the {@link RunItemEntity} containing the ID of the entity to clone;
     *                provided in the request body
     * @return a {@link ResponseEntity} containing the cloned {@link RunItemEntity}
     *         with an HTTP 200 (OK) status if the cloning succeeds;
     *         an HTTP 500 (Internal Server Error) if the cloning fails
     * @throws IOException if an I/O error occurs during the cloning process 
     *         (translated to a 500 error eventually)
     */
    @PostMapping("/init-cloning")
    public ResponseEntity<RunItemEntity> initCloning(final @RequestBody RunItemEntity runItem) throws IOException {
    	return ResponseEntity.ok(runService.initCloning(runItem.getId()));    	 
    }
}
