package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import com.sages.project2.adapters.persistence.mappers.SolutionPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaSolutionRepository;
import com.sages.project2.domain.models.Solution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SolutionPersistenceAdapterTest {

    @Mock
    private JpaSolutionRepository solutionRepository;
    @Mock
    private SolutionPersistenceMapper solutionPersistenceMapper;
    @InjectMocks
    private SolutionPersistenceAdapter solutionPersistenceAdapter;

    @Test
    void saveSolution_shouldReturnSavedSolution() throws IOException {
        // GIVEN
        var stubbedSolution = getStubbedSolution();
        var stubbedSolutionEntity = getStubbedSolutionEntity();
        // WHEN
        Mockito.when(solutionPersistenceMapper.toEntity(Mockito.any(Solution.class))).thenReturn(stubbedSolutionEntity);
        Mockito.when(solutionRepository.save(stubbedSolutionEntity)).thenReturn(stubbedSolutionEntity);
        Mockito.when(solutionPersistenceMapper.toDomain(stubbedSolutionEntity)).thenReturn(stubbedSolution);
        var savedSolution = solutionPersistenceAdapter.saveSolution(stubbedSolution);
        // THEN
        assertAll(
                () -> assertEquals(stubbedSolutionEntity.getUserId(), savedSolution.getUserId()),
                () -> assertEquals(stubbedSolutionEntity.getUsername(), savedSolution.getUsername()),
                () -> assertEquals(stubbedSolutionEntity.getQuestName(), savedSolution.getQuestName()),
                () -> assertEquals(stubbedSolutionEntity.getSolution(), savedSolution.getSolution()),
                () -> assertEquals(stubbedSolutionEntity.isResult(), savedSolution.isResult())
        );
    }

    private SolutionEntity getStubbedSolutionEntity() {
        return new SolutionEntity("Johnny", 1L, "Johnny", "Quest name", "Some solution", true);
    }

    private Solution getStubbedSolution() {
        return Solution.builder().userId(1L).username("Johnny").questName("Quest name").solution("Some solution").result(true).build();
    }


}
