package com.sages.project2.domain.services;

import com.sages.project2.commons.aop.Atomic;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.out.GithubApiRepository;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;


@RequiredArgsConstructor
public class QuestManager implements QuestService {

    private final QuestRepository questRepository;
    private final GithubApiRepository githubApiRepository;

    @Atomic
    @Override
    public Long saveQuest(Quest quest) throws IOException {
        // Create new repo on github and turn back repository object
        var repoUrl = githubApiRepository.createRepository(quest.getQuestName());
        quest.setStatus(QuestStatus.CREATED);
        quest.setRepoUrl(repoUrl);
        return questRepository.saveQuest(quest);

    }
}











