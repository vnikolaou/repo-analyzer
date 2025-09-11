package net.pionware.oss.repo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * <p>
 * Provides endpoints for listing, finding, creating, updating, and deleting run items.
 * </p>
 * 
 * @author Vangelis Nikolaou
 * @since 1.0.0
 * @see net.pionware.oss.repo.service.RunService
 */
@RestController
@RequestMapping("/api/run-items")
public class RunController {
	
    @Autowired
    private RunService runService;

    /**
     * Returns a list of {@link RunItemEntity} objects stored in the database.
     * <p>
     * If no entries are found, this method returns {@code null}.
     * </p>
     * 
     * @return the list of {@link RunItemEntity} objects, or {@code null} if no entries are found
     */    
    @GetMapping
    public ResponseEntity<List<RunItemEntity>> getAllRunItems() {
        return ResponseEntity.ok(runService.getAllRunItems());
    }
    
	@GetMapping("/{id}")
	public ResponseEntity<RunItemEntity> getRunItemById(@PathVariable Long id) {
		return ResponseEntity.ok(runService.getRunItemById(id));
	}

    @PostMapping
    public ResponseEntity<RunItemEntity> createRunItem(@RequestBody RunItemEntity runItem) {
        return ResponseEntity.ok(runService.saveRunItem(runItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunItemEntity> updateRunItem(@PathVariable Long id, @RequestBody RunItemEntity runItem) {
        return ResponseEntity.ok(runService.updateRunItem(runItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRunItem(@PathVariable Long id) {
        runService.deleteRunItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/init-cloning")
    public ResponseEntity<RunItemEntity> initCloning(@RequestBody RunItemEntity runItem) throws IOException {
    	return ResponseEntity.ok(runService.initCloning(runItem.getId()));    	 
    }
}
