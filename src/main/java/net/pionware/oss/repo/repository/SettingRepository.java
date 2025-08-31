package net.pionware.oss.repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.pionware.oss.repo.model.entity.SettingEntity;

public interface SettingRepository extends JpaRepository<SettingEntity, String> {
}