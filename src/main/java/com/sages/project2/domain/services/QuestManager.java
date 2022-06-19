package com.sages.project2.domain.services;

import com.sages.project2.commons.aop.Atomic;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.out.GitClient;
import com.sages.project2.domain.ports.out.QuestRepository;
import com.sages.project2.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
public class QuestManager implements QuestService {

    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final GitClient gitClient;

    @Atomic
    @Override
    public Long saveQuest(Quest quest) throws IOException {
        var repoUrl = gitClient.createRepository(quest.getQuestName());
        quest.setStatus(QuestStatus.CREATED);
        quest.setRepoUrl(repoUrl);
        return questRepository.saveQuest(quest);
    }

    @Atomic
    @Override
    public List<Quest> findAllQuests() {
        return questRepository.findAllQuests();
    }

    @Atomic
    @Override
    public List<Quest> findAllQuestsByDifficulty(QuestDifficulty difficulty) {
        return questRepository.findAllQuestsByDifficulty(difficulty);
    }

    @Atomic
    @Override
    public List<Quest> findAllQuestsByStatus(QuestStatus status) {
        return questRepository.findAllQuestsByStatus(status);
    }

    @Atomic
    @Override
    public List<Quest> findAllQuestsByDifficultyAndStatus(QuestDifficulty difficulty, QuestStatus status) {
        return questRepository.findAllQuestsByDifficultyAndStatus(difficulty, status);
    }

    @Override
    public void addUserToQuest(Long questId, String userLogin) {
        Quest quest = questRepository.findById(questId);
        User user = userRepository.findById(userLogin);
        quest.getUsers().add(user);
        questRepository.saveQuest(quest);
    }

}











