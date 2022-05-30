package com.sages.project2.domain.services;

import com.sages.project2.domain.ports.in.QuestVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestVerificationServiceImpl implements QuestVerificationService {

    @Override
    public boolean triggerQuest(long userId, long questId) {
        //logic should be implemented in PROJ2-7
        return false;
    }
}
