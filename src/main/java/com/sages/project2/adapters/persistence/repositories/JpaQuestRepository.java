package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.domain.models.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestRepository extends JpaRepository<QuestEntity, Long> {
    
    QuestEntity findQuestById(Long id);

    Quest toDomain(QuestEntity quest);
}
