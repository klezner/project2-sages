package com.sages.project2.domain.ports.in;

public interface QuestVerificationService {

    boolean triggerQuest(long questId, String userLogin);

}
