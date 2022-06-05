package com.sages.project2.domain.services;

import com.sages.project2.adapters.clients.GithubApiClient;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@RequiredArgsConstructor
@Service
@Transactional
public class QuestManager implements QuestService {

    private final QuestRepository questRepository;
    private final GithubApiClient githubApiClient;

    @Override
    public Quest saveQuest(Quest quest) throws IOException {
        // Create new repo on github and turn back repository object
        var repository = githubApiClient.createRepository(quest.getQuestName(),
                "Readme content",
                "commit message",
                "README.md");
        var repoUrl = repository.getHtmlUrl();
        quest.setStatus(QuestStatus.COMPLETED);
        quest.setRepoUrl(repoUrl.toString());
        return questRepository.saveQuest(quest);

    }
}











