package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import com.sages.project2.domain.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSolutionRepository extends JpaRepository<SolutionEntity, Long> {

    SolutionEntity saveSolution(SolutionEntity solutionEntity);
}
