package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.models.Quest;

import java.net.URL;

public interface QuestRepository {

    Quest saveQuest(Quest quest);

}
