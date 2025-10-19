package net.pionware.oss.repo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pionware.oss.repo.model.entity.SettingEntity;
import net.pionware.oss.repo.service.SettingService;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    private final SettingService settingService;
    
    public SettingController(final SettingService settingService) {
    	this.settingService = settingService;
    }

    @GetMapping
    public ResponseEntity<List<SettingEntity>> getAllSettings() {
    	return ResponseEntity.ok(settingService.getAllSettings());
    }

    
    @GetMapping("/{key}")
    public ResponseEntity<SettingEntity> getSettingByKey(final @PathVariable String key) {
        return ResponseEntity.ok(settingService.getSettingByKey(key));
    }
     
    @PutMapping("/{key}")
    public ResponseEntity<SettingEntity> updateSetting(final @PathVariable String key, final @RequestBody SettingEntity setting) {
    	return ResponseEntity.ok(settingService.saveSetting(setting));
    }

}