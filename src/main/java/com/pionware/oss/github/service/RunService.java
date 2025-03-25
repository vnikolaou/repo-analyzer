package com.pionware.oss.github.service;

import java.io.IOException;
import java.util.List;

import com.pionware.oss.github.entity.RunItem;

public interface RunService {
    List<RunItem> getAllRunItems();
    RunItem getRunItemById(Long id);
    RunItem saveRunItem(RunItem runItem);
    RunItem updateRunItem(RunItem runItem);
    void deleteRunItem(Long id);
    
    public RunItem initCloning(Long id) throws IOException;
}
