package net.pionware.oss.repo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pionware.oss.repo.model.entity.SettingEntity;
import net.pionware.oss.repo.repository.SettingRepository;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public List<SettingEntity> getAllSettings() {
        return settingRepository.findAll();
    }

    public SettingEntity getSettingByKey(String key) {
        SettingEntity setting = settingRepository.findById(key).orElse(null);
        validateSetting(setting);
        
        return setting;
    }

    public SettingEntity saveSetting(SettingEntity setting) {
    	validateSetting(getSettingByKey(setting.getKey()));
    	
        return settingRepository.save(setting);
    }

    public void deleteSetting(String key) {
    	SettingEntity setting = getSettingByKey(key);
    	validateSetting(setting);
    	
        settingRepository.deleteById(key);
    }
    
    private void validateSetting(SettingEntity setting) {
        if(setting == null) {
        	throw new IllegalStateException("Failed to find the setting");
        }
    }
}