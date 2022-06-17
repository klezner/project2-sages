package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.domain.QuestDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaQuestRepository extends JpaRepository<QuestEntity, Long> {

    List<QuestEntity> findAllByDifficulty(QuestDifficulty difficulty);

}
