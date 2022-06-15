package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import com.sages.project2.adapters.persistence.mappers.SolutionPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaSolutionRepository;
import com.sages.project2.domain.models.Solution;
import com.sages.project2.domain.ports.out.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@RequiredArgsConstructor
@Service
@Transactional(propagation = MANDATORY)
public class SolutionPersistenceAdapter implements SolutionRepository {

    private final JpaSolutionRepository jpaSolutionRepository;
    private final SolutionPersistenceMapper solutionPersistenceMapper;

    @Override
    public Solution saveSolution(Solution solution) throws IOException {
        var solutionEntity = solutionPersistenceMapper.toEntity(solution);
        return solutionPersistenceMapper.toDomain(jpaSolutionRepository.saveSolution(solutionEntity));
    }
}
