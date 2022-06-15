package com.sages.project2.domain.services;

import com.sages.project2.commons.aop.Atomic;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.Solution;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.SolutionService;
import com.sages.project2.domain.ports.out.DockerClient;
import com.sages.project2.domain.ports.out.GitClient;
import com.sages.project2.domain.ports.out.QuestRepository;
import com.sages.project2.domain.ports.out.SolutionRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class QuestManager implements QuestService, SolutionService {

    private final QuestRepository questRepository;
    private final GitClient gitClient;
    private final DockerClient dockerClient;
    private final SolutionRepository solutionRepository;

    @Atomic
    @Override
    public Long saveQuest(Quest quest) throws IOException {
        var repoUrl = gitClient.createRepository(quest.getQuestName());
        quest.setStatus(QuestStatus.CREATED);
        quest.setRepoUrl(repoUrl);
        return questRepository.saveQuest(quest);

    }

    @Override
    public Solution addSolution(Solution solution) throws IOException {
        var questId = solution.getQuestId();
        var username = solution.getUsername();
        var quest = questRepository.getQuest(questId);
        var questName = quest.getQuestName();
        gitClient.changeFileContentOnBranch(questName, username, solution.getSolution(), "Commited by: " + username);
        var checkSolution = dockerClient.checkSolution(questName, username);
        if (checkSolution.contains("SUCCESS")) {
            solution.setResult(true);
        } else {
            solution.setResult(false);
        }
        return solutionRepository.saveSolution(solution);
    }
}











