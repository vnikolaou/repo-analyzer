package com.pionware.oss.github.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pionware.oss.github.entity.Algorithm;
import com.pionware.oss.github.entity.RunItem;
import com.pionware.oss.github.repository.AlgorithmRepository;
import com.pionware.oss.github.repository.RunItemRepository;

@Service
public class RunServiceImpl implements RunService {
	private static final Logger logger = LogManager.getLogger(RunServiceImpl.class);
	
    @Autowired
    private RunItemRepository runItemRepository;
    @Autowired
    private AlgorithmRepository algorithmRepository;
    @Autowired
    private SettingService settingService;
    @Autowired
    private RepoService repoService;
    
    @Override
    public List<RunItem> getAllRunItems() {
        return runItemRepository.findAll();
    }

    @Override
    public RunItem getRunItemById(Long id) {
    	RunItem item = runItemRepository.findById(id).orElse(null);
    	validateRunItem(item);
        
        return item;
    }
    
    @Override
    public RunItem saveRunItem(RunItem runItem) {
    	Algorithm algorithm = algorithmRepository.findById(1L).orElse(null);
        runItem.setAlgorithm(algorithm);

        String maxLimitValue = settingService.getSettingByKey("MAX_LIMIT").getValue();
        runItem.setMaxLimit(Long.parseLong(maxLimitValue));

        runItem.setCreatedAt(LocalDateTime.now());
        runItem.setStatus("INITIALIZED");
        
        return runItemRepository.save(runItem);
    }

    @Override
    public RunItem updateRunItem(RunItem runItem) {
    	RunItem item = getRunItemById(runItem.getId());
    	validateRunItem(item);
    	
        return runItemRepository.save(runItem);
   }
    
    @Override
    public void deleteRunItem(Long id) {
        RunItem runItem = getRunItemById(id);
        validateRunItem(runItem);
        
        String clonePath = settingService.getSettingByKey("CLONE_PATH").getValue();
     
        File folder = new File(clonePath, id.toString());
        boolean deleted = deleteDirectory(folder);
        logger.info("Folder {} was deleted: {}", folder.getName(), deleted);
  
        if (!deleted && folder.exists()) {
        	throw new IllegalStateException("Failed to delete directory: " + folder.getAbsolutePath());
        }

        repoService.deleteRepositories(runItem);
        
        runItemRepository.delete(runItem);
    }
    
    public boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!deleteDirectory(file)) {
                        return false;
                    }
                }
            }
        }
        return directory.delete();
    }
    
    @Override
    public RunItem initCloning(Long id) {
        RunItem runItem = getRunItemById(id);
        validateRunItem(runItem);

        String clonePath = settingService.getSettingByKey("CLONE_PATH").getValue();
     
        File folder = new File(clonePath, id.toString());
        boolean created = folder.mkdirs();
        logger.info("Folder {} was created: {}", folder.getName(), created);
  
        if (!created && folder.exists()) {
        	throw new IllegalStateException("Failed to create directory: " + folder.getAbsolutePath());
        }
            
        runItem.setStatus("SELECTION_STARTED");
        runItemRepository.save(runItem);
     
        return runItem;
    }
    

    private void validateRunItem(RunItem runItem) {
        if(runItem == null) {
        	throw new IllegalStateException("Failed to find the run item");
        }
    }
}
