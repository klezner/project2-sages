package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaQuestRepository extends JpaRepository<QuestEntity, Long> {
    
    QuestEntity findQuestById(Long id);

    List<QuestEntity> findAllByDifficulty(QuestDifficulty difficulty);

    List<QuestEntity> findAllByStatus(QuestStatus status);

    List<QuestEntity> findAllByDifficultyAndStatus(QuestDifficulty difficulty, QuestStatus status);

}
