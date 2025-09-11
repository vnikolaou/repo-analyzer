package net.pionware.oss.repo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.transaction.Transactional;
import net.pionware.oss.repo.model.entity.RepoItemEntity;
import net.pionware.oss.repo.model.entity.RunItemEntity;
import net.pionware.oss.repo.model.response.RepoResponse;
import net.pionware.oss.repo.repository.RepoItemRepository;
import net.pionware.oss.repo.repository.RunItemRepository;

@Service
public class RepoServiceImpl implements RepoService {
	private static final Logger logger = LogManager.getLogger(RepoServiceImpl.class);

	final private SettingService settingService;
    final private RepoItemRepository repoItemRepository;
    final private RunItemRepository runItemRepository;
    
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Spring-managed bean injection; safe to expose")
    public RepoServiceImpl(SettingService settingService, RepoItemRepository repoItemRepository, RunItemRepository runItemRepository) { 
    	this.settingService = settingService;
    	this.repoItemRepository = repoItemRepository;
    	this.runItemRepository = runItemRepository;
    }
    
	@Override
	public int saveRepositories(RunItemEntity runItem, List<RepoResponse> repos) {
		if(repos == null) return 0;
		
		List<RepoItemEntity> repoItems = new ArrayList<>(repos.size());
        for (RepoResponse repo : repos) {
            RepoItemEntity repoItem = new RepoItemEntity();
            
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
	public List<RepoResponse>  getRepositories(Long runId) {

		List<RepoItemEntity> repoItems = repoItemRepository.findByRunItemIdOrderByCreatedAtAsc(runId);
		List<RepoResponse> repos = new ArrayList<>();
        for (RepoItemEntity repoItem : repoItems) {
        	
            RepoResponse repo = new RepoResponse(repoItem.getId(), repoItem.getRepoId(),repoItem.getIsPrivate(), repoItem.getFullName(), repoItem.getStars(), repoItem.getSshUrl(),
            		repoItem.getCloneUrl(), repoItem.getSize(), repoItem.getWatchers(), repoItem.getHasIssues(), repoItem.getForks(), 
            		repoItem.getOpenIssues(), repoItem.getLicense(), repoItem.isCloned(), repoItem.isChosen(), repoItem.getCommits(), repoItem.getContributors(), 
            		repoItem.getCreatedAt(), repoItem.getUpdatedAt(), repoItem.getPushedAt(), repoItem.getAnalyzed(), repoItem.getCoverage(), repoItem.getFailed(), 
            		repoItem.getComments(), repoItem.getCommitId(), repoItem.getComplexity());
            
            repos.add(repo);
        }
        
		return repos;
	}

	@Transactional
	public void deleteRepositories(RunItemEntity runItem) {
		repoItemRepository.deleteByRunItemId(runItem.getId());
	}

	public RepoResponse getRepository(Long id) {
		RepoItemEntity repoItem = repoItemRepository.findById(id).get();
		
		RepoResponse repo = new RepoResponse(repoItem.getId(), repoItem.getRepoId(),repoItem.getIsPrivate(), repoItem.getFullName(), repoItem.getStars(), repoItem.getSshUrl(),
        		repoItem.getCloneUrl(), repoItem.getSize(), repoItem.getWatchers(), repoItem.getHasIssues(), repoItem.getForks(), 
        		repoItem.getOpenIssues(), repoItem.getLicense(), repoItem.isCloned(), repoItem.isChosen(), repoItem.getCommits(), repoItem.getContributors(), 
        		repoItem.getCreatedAt(), repoItem.getUpdatedAt(), repoItem.getPushedAt(), repoItem.getAnalyzed(), repoItem.getCoverage(), repoItem.getFailed(), 
        		repoItem.getComments(), repoItem.getCommitId(), repoItem.getComplexity());
		
		return repo;
	}
	
	@Override
	@Transactional
	public RepoResponse updateRepository(Long id, RepoResponse repo) {
		RepoItemEntity repoItem = repoItemRepository.findById(id).get();
		
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
		
		RepoItemEntity updatedRepoItem = repoItemRepository.save(repoItem);
		
		
		RepoResponse updateRepo = new RepoResponse(updatedRepoItem.getId(), updatedRepoItem.getRepoId(),updatedRepoItem.getIsPrivate(), updatedRepoItem.getFullName(), updatedRepoItem.getStars(), updatedRepoItem.getSshUrl(),
				updatedRepoItem.getCloneUrl(), updatedRepoItem.getSize(), updatedRepoItem.getWatchers(), updatedRepoItem.getHasIssues(), updatedRepoItem.getForks(), 
				updatedRepoItem.getOpenIssues(), updatedRepoItem.getLicense(), updatedRepoItem.isCloned(), updatedRepoItem.isChosen(), updatedRepoItem.getCommits(), updatedRepoItem.getContributors(), 
				updatedRepoItem.getCreatedAt(), updatedRepoItem.getUpdatedAt(), updatedRepoItem.getPushedAt(), updatedRepoItem.getAnalyzed(), updatedRepoItem.getCoverage(), updatedRepoItem.getFailed(), 
				updatedRepoItem.getComments(), updatedRepoItem.getCommitId(), updatedRepoItem.getComplexity());
		
		return updateRepo;
	}
	
    public Map<Integer, List<RepoItemEntity>> getSample(Long runId) {
        // Step 1: Fetch repositories from the database for the given run ID
        List<RepoItemEntity> repoItems = repoItemRepository.findByRunItemIdOrderByCreatedAtAsc(runId);

        // Step 2: Filter by MIT and Apache-2.0 licenses
        List<RepoItemEntity> filteredRepoItems = filterByLicense(repoItems);

        // Step 3: Group repositories by creation year
        Map<Integer, List<RepoItemEntity>> groupedRepoItems = groupByCreationYear(filteredRepoItems);

        // Step 4: Shuffle and select samples per year
        int maxNumber = Integer.parseInt(settingService.getSettingByKey("SAMPLE_NUM").getValue());
        logger.info("Sampling {} repositories per year", maxNumber);
        Map<Integer, List<RepoItemEntity>> sampledRepos = shuffleRepositories(groupedRepoItems, maxNumber);

        // Step 5: Save sampled repositories to the database
        saveSampledRepositories(sampledRepos);
     
        // Step 6: update status
        RunItemEntity runItem = runItemRepository.findById(runId).get();
        runItem.setStatus("ANALYIS_STARTED");
        runItemRepository.save(runItem);
        
        return sampledRepos;
    }

    // Filters repositories based on accepted licenses (MIT, Apache-2.0)
    private List<RepoItemEntity> filterByLicense(List<RepoItemEntity> repoItems) {
       // Set<String> acceptedLicenses = Set.of("", "other");
        return repoItems.stream()
            .filter(repo -> repo.getLicense() != null && !repo.getLicense().isEmpty() && !repo.getLicense().equalsIgnoreCase("other"))
            .collect(Collectors.toList());
    }

    // Groups repositories by creation year
    private Map<Integer, List<RepoItemEntity>> groupByCreationYear(List<RepoItemEntity> repoItems) {
        Map<Integer, List<RepoItemEntity>> grouped = new HashMap<>();
        for (RepoItemEntity repo : repoItems) {
            int year = LocalDateTime.ofInstant(repo.getCreatedAt(), ZoneId.of("UTC")).getYear();
            grouped.putIfAbsent(year, new ArrayList<>());
            grouped.get(year).add(repo);
        }
        return grouped;
    }

    // Shuffles repositories and selects up to the target number per year
    private Map<Integer, List<RepoItemEntity>> shuffleRepositories(Map<Integer, List<RepoItemEntity>> groupedRepos, int expectedTotal) {
        Map<Integer, List<RepoItemEntity>> sampledRepositories = new HashMap<>();
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
        for (Map.Entry<Integer, List<RepoItemEntity>> entry : groupedRepos.entrySet()) {
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
        for (Map.Entry<Integer, List<RepoItemEntity>> entry : groupedRepos.entrySet()) {
            List<RepoItemEntity> repos = new ArrayList<>(entry.getValue());
            Collections.shuffle(repos, random);

            int numToSelect = allocationPerYear.getOrDefault(entry.getKey(), 0);
            logger.info("{} selected repositories for year {} ", numToSelect, entry.getKey());
            List<RepoItemEntity> selected = repos.stream().limit(numToSelect).collect(Collectors.toList());

            sampledRepositories.put(entry.getKey(), selected);
        }

        return sampledRepositories;
    }
    
    @Transactional
    public void saveSampledRepositories(Map<Integer, List<RepoItemEntity>> sampledRepositories) {
    	List<RepoItemEntity> entities = sampledRepositories.values().stream()
                .flatMap(List::stream)
                .map(repo -> {
                	repo.setChosen(true);
                	return repo;
                })
                .collect(Collectors.toList());

            repoItemRepository.saveAll(entities);
    }
    
}
