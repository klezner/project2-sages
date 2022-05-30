package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestRepository extends JpaRepository<QuestEntity, Long> {
}
