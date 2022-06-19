package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.models.Quest;

import java.util.List;

public interface QuestRepository {

    Long saveQuest(Quest quest);

    List<Quest> findAllQuests();

    List<Quest> findAllQuestsByDifficulty(QuestDifficulty difficulty);

    List<Quest> findAllQuestsByStatus(QuestStatus status);

    List<Quest> findAllQuestsByDifficultyAndStatus(QuestDifficulty difficulty, QuestStatus status);

    Quest findById(Long questId);
}
