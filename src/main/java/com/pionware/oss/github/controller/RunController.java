package com.pionware.oss.github.controller;

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

import com.pionware.oss.github.entity.RunItem;
import com.pionware.oss.github.service.RunService;

@RestController
@RequestMapping("/api/run-items")
public class RunController {

    @Autowired
    private RunService runService;

    
    @GetMapping
    public ResponseEntity<List<RunItem>> getAllRunItems() {
        return ResponseEntity.ok(runService.getAllRunItems());
    }
    
	@GetMapping("/{id}")
	public ResponseEntity<RunItem> getRunItemById(@PathVariable Long id) {
		return ResponseEntity.ok(runService.getRunItemById(id));
	}

    @PostMapping
    public ResponseEntity<RunItem> createRunItem(@RequestBody RunItem runItem) {
        return ResponseEntity.ok(runService.saveRunItem(runItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunItem> updateRunItem(@PathVariable Long id, @RequestBody RunItem runItem) {
        return ResponseEntity.ok(runService.updateRunItem(runItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRunItem(@PathVariable Long id) {
        runService.deleteRunItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/init-cloning")
    public ResponseEntity<RunItem> initCloning(@RequestBody RunItem runItem) throws IOException {
    	return ResponseEntity.ok(runService.initCloning(runItem.getId()));    	 
    }
}
