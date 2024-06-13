package com.example.spring_microservice_proxy.services;

import com.example.spring_microservice_proxy.repositories.AIResultJPAEntity;
import com.example.spring_microservice_proxy.repositories.AIResultsRepository;
import com.example.spring_microservice_proxy.services.ai_results.AIAPIRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AIResultsService {

    private final AIResultsRepository repository;
    private final AIAPIRequestService aIAPIRequestService;

    @Autowired
    public AIResultsService(AIResultsRepository repository, AIAPIRequestService aIAPIRequestService) {
        this.repository = repository;
        this.aIAPIRequestService = aIAPIRequestService;
    }

    public Optional<AIResultJPAEntity> find(String name, double splitPercentage, Integer alg) {
        return repository.findByNameAndSplitAndAlgorithmEquals(name, splitPercentage, alg);
    }

    public AIResultJPAEntity createNew(String name, Double splitPercentage, Integer alg) {
        String result = aIAPIRequestService.getResults(name, splitPercentage, alg);

        AIResultJPAEntity entity = new AIResultJPAEntity();
        entity.setName(name);
        entity.setRequestedDate(Instant.now());
        entity.setContent(result);
        entity.setSplit(splitPercentage);
        entity.setAlgorithm(alg);

        return repository.save(entity);
    }
}
