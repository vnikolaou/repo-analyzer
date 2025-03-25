package com.pionware.oss.github.service;

import java.util.List;

import com.pionware.oss.github.entity.Setting;

public interface SettingService {
	public List<Setting> getAllSettings();	
	public Setting getSettingByKey(String key);
	public Setting saveSetting(Setting setting);
	public void deleteSetting(String key);


}
