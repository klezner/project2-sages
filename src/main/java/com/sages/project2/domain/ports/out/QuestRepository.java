package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.models.Quest;

import java.util.List;

public interface QuestRepository {

    Long saveQuest(Quest quest);

    List<Quest> findAllQuests();

}
