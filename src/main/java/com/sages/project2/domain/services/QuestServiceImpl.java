package com.sages.project2.domain.services;

import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.QuestVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestServiceImpl implements QuestService {

    private final QuestVerificationService verificationService;

    @Override
    public boolean check(long userId, long questId) {
        return verificationService.triggerQuest(userId, questId);
    }
}
