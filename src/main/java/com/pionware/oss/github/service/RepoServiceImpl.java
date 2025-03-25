package com.pionware.oss.github.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pionware.oss.github.entity.RepoItem;
import com.pionware.oss.github.entity.RunItem;
import com.pionware.oss.github.model.Repo;
import com.pionware.oss.github.repository.RepoItemRepository;
import com.pionware.oss.github.repository.RunItemRepository;

import jakarta.transaction.Transactional;

@Service
public class RepoServiceImpl implements RepoService {
	private static final Logger logger = LogManager.getLogger(RepoServiceImpl.class);

	private SettingService settingService;
    private RepoItemRepository repoItemRepository;
    private RunItemRepository runItemRepository;
    
    public RepoServiceImpl(SettingService settingService, RepoItemRepository repoItemRepository, RunItemRepository runItemRepository) { 
    	this.settingService = settingService;
    	this.repoItemRepository = repoItemRepository;
    	this.runItemRepository = runItemRepository;
    }
    
	@Override
	public int saveRepositories(RunItem runItem, List<Repo> repos) {
		if(repos == null) return 0;
		
		List<RepoItem> repoItems = new ArrayList<>(repos.size());
        for (Repo repo : repos) {
            RepoItem repoItem = new RepoItem();
            
            repoItem.setRepoId(repo.repoId());
            repoItem.setCloneUrl(repo.cloneUrl());
            repoItem.setForks(repo.forks());
            repoItem.setFullName(repo.fullName());
            repoItem.setHasIssues(repo.hasIssues());
            repoItem.setIsPrivate(repo.isPrivate());
            repoItem.setLicense(repo.license());
            repoItem.setOpenIssues(repo.openIssues());
            repoItem.setRunItem(runItem);
            repoItem.setSize(repo.size());
            repoItem.setSshUrl(repo.sshUrl());
            repoItem.setStars(repo.stars());
            repoItem.setWatchers(repo.watchers());
            repoItem.setCreatedAt(repo.createdAt());
            repoItem.setPushedAt(repo.pushedAt());     
            repoItem.setUpdatedAt(repo.updatedAt());  
            repoItem.setComments(repo.comments());
            repoItem.setCommitId(repo.commitId());
            repoItem.setComplexity(repo.complexity());
            
            repoItems.add(repoItem);
        }
        
        repoItemRepository.saveAll(repoItems);
        
        return repoItems.size();
	}

	@Override
	public List<Repo>  getRepositories(Long runId) {

		List<RepoItem> repoItems = repoItemRepository.findByRunItemIdOrderByCreatedAtAsc(runId);
		List<Repo> repos = new ArrayList<>();
        for (RepoItem repoItem : repoItems) {
        	
            Repo repo = new Repo(repoItem.getId(), repoItem.getRepoId(),repoItem.getIsPrivate(), repoItem.getFullName(), repoItem.getStars(), repoItem.getSshUrl(),
            		repoItem.getCloneUrl(), repoItem.getSize(), repoItem.getWatchers(), repoItem.getHasIssues(), repoItem.getForks(), 
            		repoItem.getOpenIssues(), repoItem.getLicense(), repoItem.isCloned(), repoItem.isChosen(), repoItem.getCommits(), repoItem.getContributors(), 
            		repoItem.getCreatedAt(), repoItem.getUpdatedAt(), repoItem.getPushedAt(), repoItem.getAnalyzed(), repoItem.getCoverage(), repoItem.getFailed(), 
            		repoItem.getComments(), repoItem.getCommitId(), repoItem.getComplexity());
            
            repos.add(repo);
        }
        
		return repos;
	}

	@Transactional
	public void deleteRepositories(RunItem runItem) {
		repoItemRepository.deleteByRunItemId(runItem.getId());
	}

	public Repo getRepository(Long id) {
		RepoItem repoItem = repoItemRepository.findById(id).get();
		
		Repo repo = new Repo(repoItem.getId(), repoItem.getRepoId(),repoItem.getIsPrivate(), repoItem.getFullName(), repoItem.getStars(), repoItem.getSshUrl(),
        		repoItem.getCloneUrl(), repoItem.getSize(), repoItem.getWatchers(), repoItem.getHasIssues(), repoItem.getForks(), 
        		repoItem.getOpenIssues(), repoItem.getLicense(), repoItem.isCloned(), repoItem.isChosen(), repoItem.getCommits(), repoItem.getContributors(), 
        		repoItem.getCreatedAt(), repoItem.getUpdatedAt(), repoItem.getPushedAt(), repoItem.getAnalyzed(), repoItem.getCoverage(), repoItem.getFailed(), 
        		repoItem.getComments(), repoItem.getCommitId(), repoItem.getComplexity());
		
		return repo;
	}
	
	@Override
	@Transactional
	public Repo updateRepository(Long id, Repo repo) {
		RepoItem repoItem = repoItemRepository.findById(id).get();
		
		repoItem.setAnalyzed(repo.analyzed());
		repoItem.setCoverage(repo.coverage());
		repoItem.setFailed(repo.failed());
		repoItem.setCommits(repo.commits());
		repoItem.setContributors(repo.contributors());
		repoItem.setCloned(repo.cloned());
		repoItem.setChosen(repo.chosen());
		repoItem.setComments(repo.comments());
		repoItem.setCommitId(repo.commitId());
		repoItem.setComplexity(repo.complexity());
		
		RepoItem updatedRepoItem = repoItemRepository.save(repoItem);
		
		
		Repo updateRepo = new Repo(updatedRepoItem.getId(), updatedRepoItem.getRepoId(),updatedRepoItem.getIsPrivate(), updatedRepoItem.getFullName(), updatedRepoItem.getStars(), updatedRepoItem.getSshUrl(),
				updatedRepoItem.getCloneUrl(), updatedRepoItem.getSize(), updatedRepoItem.getWatchers(), updatedRepoItem.getHasIssues(), updatedRepoItem.getForks(), 
				updatedRepoItem.getOpenIssues(), updatedRepoItem.getLicense(), updatedRepoItem.isCloned(), updatedRepoItem.isChosen(), updatedRepoItem.getCommits(), updatedRepoItem.getContributors(), 
				updatedRepoItem.getCreatedAt(), updatedRepoItem.getUpdatedAt(), updatedRepoItem.getPushedAt(), updatedRepoItem.getAnalyzed(), updatedRepoItem.getCoverage(), updatedRepoItem.getFailed(), 
				updatedRepoItem.getComments(), updatedRepoItem.getCommitId(), updatedRepoItem.getComplexity());
		
		return updateRepo;
	}
	
    public Map<Integer, List<RepoItem>> getSample(Long runId) {
        // Step 1: Fetch repositories from the database for the given run ID
        List<RepoItem> repoItems = repoItemRepository.findByRunItemIdOrderByCreatedAtAsc(runId);

        // Step 2: Filter by MIT and Apache-2.0 licenses
        List<RepoItem> filteredRepoItems = filterByLicense(repoItems);

        // Step 3: Group repositories by creation year
        Map<Integer, List<RepoItem>> groupedRepoItems = groupByCreationYear(filteredRepoItems);

        // Step 4: Shuffle and select samples per year
        int maxNumber = Integer.parseInt(settingService.getSettingByKey("SAMPLE_NUM").getValue());
        logger.info("Sampling {} repositories per year", maxNumber);
        Map<Integer, List<RepoItem>> sampledRepos = shuffleRepositories(groupedRepoItems, maxNumber);

        // Step 5: Save sampled repositories to the database
        saveSampledRepositories(sampledRepos);
     
        // Step 6: update status
        RunItem runItem = runItemRepository.findById(runId).get();
        runItem.setStatus("ANALYIS_STARTED");
        runItemRepository.save(runItem);
        
        return sampledRepos;
    }

    // Filters repositories based on accepted licenses (MIT, Apache-2.0)
    private List<RepoItem> filterByLicense(List<RepoItem> repoItems) {
       // Set<String> acceptedLicenses = Set.of("", "other");
        return repoItems.stream()
            .filter(repo -> repo.getLicense() != null && !repo.getLicense().isEmpty() && !repo.getLicense().equalsIgnoreCase("other"))
            .collect(Collectors.toList());
    }

    // Groups repositories by creation year
    private Map<Integer, List<RepoItem>> groupByCreationYear(List<RepoItem> repoItems) {
        Map<Integer, List<RepoItem>> grouped = new HashMap<>();
        for (RepoItem repo : repoItems) {
            int year = LocalDateTime.ofInstant(repo.getCreatedAt(), ZoneId.of("UTC")).getYear();
            grouped.putIfAbsent(year, new ArrayList<>());
            grouped.get(year).add(repo);
        }
        return grouped;
    }

    // Shuffles repositories and selects up to the target number per year
    private Map<Integer, List<RepoItem>> shuffleRepositories(Map<Integer, List<RepoItem>> groupedRepos, int expectedTotal) {
        Map<Integer, List<RepoItem>> sampledRepositories = new HashMap<>();
        Random random = new Random();

        // Calculate total number of available repositories across all years
        int totalAvailable = groupedRepos.values().stream().mapToInt(List::size).sum();
        logger.info("Total repositories available for sampling: {}", totalAvailable);
        // Avoid division by zero if there are no repositories
        if (totalAvailable == 0) {
            return sampledRepositories;
        }

        // Calculate proportional allocation for each year
        Map<Integer, Integer> allocationPerYear = new HashMap<>();
        for (Map.Entry<Integer, List<RepoItem>> entry : groupedRepos.entrySet()) {
            int proportionalCount = (int) Math.round(((double) entry.getValue().size() / totalAvailable) * expectedTotal);
            logger.info("Proportional count for year {}: {} ", entry.getKey(), proportionalCount);
            allocationPerYear.put(entry.getKey(), proportionalCount);
        }

        // Adjust to ensure the total does not exceed expectedTotal
        int actualTotal = allocationPerYear.values().stream().mapToInt(Integer::intValue).sum();
        if (actualTotal > expectedTotal) {
            List<Integer> keys = new ArrayList<>(allocationPerYear.keySet());
            while (actualTotal > expectedTotal) {
                int key = keys.get(random.nextInt(keys.size()));
                if (allocationPerYear.get(key) > 0) {
                    allocationPerYear.put(key, allocationPerYear.get(key) - 1);
                    actualTotal--;
                }
            }
        }

        // Shuffle and select repositories based on proportional allocation
        for (Map.Entry<Integer, List<RepoItem>> entry : groupedRepos.entrySet()) {
            List<RepoItem> repos = new ArrayList<>(entry.getValue());
            Collections.shuffle(repos, random);

            int numToSelect = allocationPerYear.getOrDefault(entry.getKey(), 0);
            logger.info("{} selected repositories for year {} ", numToSelect, entry.getKey());
            List<RepoItem> selected = repos.stream().limit(numToSelect).collect(Collectors.toList());

            sampledRepositories.put(entry.getKey(), selected);
        }

        return sampledRepositories;
    }
    
    @Transactional
    public void saveSampledRepositories(Map<Integer, List<RepoItem>> sampledRepositories) {
    	List<RepoItem> entities = sampledRepositories.values().stream()
                .flatMap(List::stream)
                .map(repo -> {
                	repo.setChosen(true);
                	return repo;
                })
                .collect(Collectors.toList());

            repoItemRepository.saveAll(entities);
    }
    
}
