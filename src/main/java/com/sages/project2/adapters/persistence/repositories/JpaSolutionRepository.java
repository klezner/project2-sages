package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSolutionRepository extends JpaRepository<SolutionEntity, Long> {

//    SolutionEntity save(SolutionEntity solutionEntity);

}
