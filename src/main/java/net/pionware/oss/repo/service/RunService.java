package net.pionware.oss.repo.service;

import java.io.IOException;
import java.util.List;

import net.pionware.oss.repo.model.entity.RunItemEntity;

public interface RunService {
    List<RunItemEntity> getAllRunItems();
    RunItemEntity getRunItemById(Long id);
    RunItemEntity saveRunItem(RunItemEntity runItem);
    RunItemEntity updateRunItem(RunItemEntity runItem);
    void deleteRunItem(Long id);
    
    public RunItemEntity initCloning(Long id) throws IOException;
}
