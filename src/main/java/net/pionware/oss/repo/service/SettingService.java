package net.pionware.oss.repo.service;

import java.util.List;

import net.pionware.oss.repo.model.entity.SettingEntity;

public interface SettingService {
	public List<SettingEntity> getAllSettings();	
	public SettingEntity getSettingByKey(String key);
	public SettingEntity saveSetting(SettingEntity setting);
	public void deleteSetting(String key);


}
