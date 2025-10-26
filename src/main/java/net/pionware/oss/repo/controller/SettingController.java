package net.pionware.oss.repo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pionware.oss.repo.model.entity.SettingEntity;
import net.pionware.oss.repo.service.RunService;
import net.pionware.oss.repo.service.SettingService;

/**
 * REST controller for managing application settings.
 *
 * <p>Provides endpoints to retrieve and update {@link SettingEntity} records.</p>
 * 
 * @author Vangelis Nikolaou
 * @since 1.0.0
 * @see net.pionware.oss.repo.service.SettingService
 */
@RestController
@RequestMapping("/api/settings")
public class SettingController {

    /**
     * Service component that provides business logic for managing
     * {@link SettingEntity} instances.</p>
     */
    private final SettingService settingService;

    /**
     * Constructs a new {@code SettingController} with the specified {@link SettingService}.
     *
     * <p>This constructor is typically used for dependency injection.</p>
     *
     * @param settingService the {@link SettingService} service used to handle operations related to settings
     */
    public SettingController(final SettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * Retrieves all existing {@link SettingEntity} records.
     *
     * <p>Invoked via an HTTP GET request, and returns a list of all settings 
     * available in the system. If no settings exist, an empty list is returned.</p>
     *
     * @return a {@link ResponseEntity} containing a list of {@link SettingEntity}
     *         objects and an HTTP 200 (OK) status
     */
    @GetMapping
    public ResponseEntity<List<SettingEntity>> getAllSettings() {
        return ResponseEntity.ok(settingService.getAllSettings());
    }

    /**
     * Retrieves a specific {@link SettingEntity} by its unique key.
     *
     * <p>Invoked via an HTTP GET request. If a matching setting is found, 
     * it is returned with HTTP 200 (OK); otherwise, a 500 (Internal Server Error) 
     * response is returned through a global handler that catches a {@link IllegalStateException} 
     * exception thrown by the {@link RunService}.</p>
     *
     * @param key the unique key identifying the {@link SettingEntity}
     * @return a {@link ResponseEntity} containing the requested {@link SettingEntity}; 
     *         otherwise a 500 (Internal Server Error) response is returned
     */
    @GetMapping("/{key}")
    public ResponseEntity<SettingEntity> getSettingByKey(final @PathVariable String key) {
        return ResponseEntity.ok(settingService.getSettingByKey(key));
    }

    /**
     * Updates an existing {@link SettingEntity} identified by its unique key.
     *
     * <p>Invoked via an HTTP PUT request with the updated {@link SettingEntity} 
     * in the request body. If the update succeeds, the updated entity is returned 
     * with HTTP 200 (OK). If the update fails for any reason, a 500 (Internal Server Error) 
     * response is returned through a global handler that catches a {@link IllegalStateException} 
     * exception thrown by the {@link RunService}.</p>
     *
     * @param key the unique key of the {@link SettingEntity} to update
     * @param setting the updated {@link SettingEntity} data provided in the request body
     * @return a {@link ResponseEntity} containing the updated {@link SettingEntity}
     *         if the update succeeds; otherwise a 500 (Internal Server Error) response is returned
     */
    @PutMapping("/{key}")
    public ResponseEntity<SettingEntity> updateSetting(
            final @PathVariable String key,
            final @RequestBody SettingEntity setting) {
        return ResponseEntity.ok(settingService.saveSetting(setting));
    }

}
