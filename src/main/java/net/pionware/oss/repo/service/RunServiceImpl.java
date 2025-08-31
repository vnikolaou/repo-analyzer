package net.pionware.oss.repo.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pionware.oss.repo.model.entity.RunItemEntity;
import net.pionware.oss.repo.repository.RunItemRepository;

@Service
public class RunServiceImpl implements RunService {
	private static final Logger logger = LogManager.getLogger(RunServiceImpl.class);
	
    @Autowired
    private RunItemRepository runItemRepository;
    @Autowired
    private SettingService settingService;
    @Autowired
    private RepoService repoService;
    
    @Override
    public List<RunItemEntity> getAllRunItems() {
        return runItemRepository.findAll();
    }

    @Override
    public RunItemEntity getRunItemById(Long id) {
    	RunItemEntity item = runItemRepository.findById(id).orElse(null);
    	validateRunItem(item);
        
        return item;
    }
    
    @Override
    public RunItemEntity saveRunItem(RunItemEntity runItem) {

        String maxLimitValue = settingService.getSettingByKey("MAX_LIMIT").getValue();
        runItem.setMaxLimit(Long.parseLong(maxLimitValue));

        runItem.setCreatedAt(LocalDateTime.now());
        runItem.setStatus("INITIALIZED");
        
        return runItemRepository.save(runItem);
    }

    @Override
    public RunItemEntity updateRunItem(RunItemEntity runItem) {
    	RunItemEntity item = getRunItemById(runItem.getId());
    	validateRunItem(item);
    	
        return runItemRepository.save(runItem);
   }
    
    @Override
    public void deleteRunItem(Long id) {
        RunItemEntity runItem = getRunItemById(id);
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
    public RunItemEntity initCloning(Long id) {
        RunItemEntity runItem = getRunItemById(id);
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
    

    private void validateRunItem(RunItemEntity runItem) {
        if(runItem == null) {
        	throw new IllegalStateException("Failed to find the run item");
        }
    }
}
