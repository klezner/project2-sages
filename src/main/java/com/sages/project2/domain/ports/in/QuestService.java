package com.sages.project2.domain.ports.in;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.models.Quest;

import java.io.IOException;
import java.util.List;

public interface QuestService {

     Long saveQuest(Quest quest) throws IOException;

     List<Quest> findAllQuests();

     List<Quest> findAllQuestsByDifficulty(QuestDifficulty difficulty);

     void addUserToQuest(Long questId, String userLogin);
}

