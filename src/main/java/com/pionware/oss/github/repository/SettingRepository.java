package com.pionware.oss.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pionware.oss.github.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {
}