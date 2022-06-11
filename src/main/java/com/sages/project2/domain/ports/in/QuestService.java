package com.sages.project2.domain.ports.in;

import com.sages.project2.domain.models.Quest;

import java.io.IOException;

public interface QuestService {

     Long saveQuest(Quest quest) throws IOException;
}
