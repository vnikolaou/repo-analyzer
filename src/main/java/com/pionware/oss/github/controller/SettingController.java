package com.pionware.oss.github.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pionware.oss.github.entity.Setting;
import com.pionware.oss.github.service.SettingService;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping
    public ResponseEntity<List<Setting>> getAllSettings() {
    	return ResponseEntity.ok(settingService.getAllSettings());
    }

    
    @GetMapping("/{key}")
    public ResponseEntity<Setting> getSettingByKey(@PathVariable String key) {
        return ResponseEntity.ok(settingService.getSettingByKey(key));
    }
    
    /*
    @PostMapping
    public Setting createSetting(@RequestBody Setting setting) {
        return settingService.saveSetting(setting);
    }
*/
    
    @PutMapping("/{key}")
    public ResponseEntity<Setting> updateSetting(@PathVariable String key, @RequestBody Setting setting) {
    	return ResponseEntity.ok(settingService.saveSetting(setting));
    }

}