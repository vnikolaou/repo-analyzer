package com.pionware.oss.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pionware.oss.github.entity.Setting;
import com.pionware.oss.github.repository.SettingRepository;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    public Setting getSettingByKey(String key) {
        Setting setting = settingRepository.findById(key).orElse(null);
        validateSetting(setting);
        
        return setting;
    }

    public Setting saveSetting(Setting setting) {
    	validateSetting(getSettingByKey(setting.getKey()));
    	
        return settingRepository.save(setting);
    }

    public void deleteSetting(String key) {
    	Setting setting = getSettingByKey(key);
    	validateSetting(setting);
    	
        settingRepository.deleteById(key);
    }
    
    private void validateSetting(Setting setting) {
        if(setting == null) {
        	throw new IllegalStateException("Failed to find the setting");
        }
    }
}