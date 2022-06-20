package com.sages.project2.domain.services;

import com.sages.project2.domain.ports.in.QuestVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestVerificationTrigger implements QuestVerificationService {

    @Override
    public boolean triggerQuest(long questId, String userLogin) {
        // TODO: to implement getting branch and check solution
        return false;
    }
}
