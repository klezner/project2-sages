package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.models.Quest;

public interface QuestRepository {

    Quest updateQuest(Quest quest);

}
