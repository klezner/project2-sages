package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaSolutionRepositoryTest {

    @Autowired
    private JpaSolutionRepository solutionRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_shouldFindOnlyOneEntity() {
        assertTrue(solutionRepository.findAll().isEmpty());
        var solutionEntity = new SolutionEntity("Johnny", 1L, "Johnny", "Quest name", "Some solution", false);
        solutionRepository.save(solutionEntity);
        var foundEntities = solutionRepository.findAll();
        assertAll(
                () -> assertEquals(1, foundEntities.size()),
                () -> assertEquals(foundEntities.get(0).getLogin(), solutionEntity.getLogin()),
                () -> assertEquals(foundEntities.get(0).getUserId(), solutionEntity.getUserId()),
                () -> assertEquals(foundEntities.get(0).getUsername(), solutionEntity.getUsername()),
                () -> assertEquals(foundEntities.get(0).getQuestName(), solutionEntity.getQuestName()),
                () -> assertEquals(foundEntities.get(0).getSolution(), solutionEntity.getSolution()),
                () -> assertEquals(foundEntities.get(0).isResult(), solutionEntity.isResult())
        );
    }

}
