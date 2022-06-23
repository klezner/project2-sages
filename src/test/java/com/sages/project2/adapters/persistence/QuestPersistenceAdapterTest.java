package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.mappers.QuestPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class QuestPersistenceAdapterTest {

    @Mock
    private JpaQuestRepository questRepository;
    @Mock
    private QuestPersistenceMapper questPersistenceMapper;
    @InjectMocks
    private QuestPersistenceAdapter questPersistenceAdapter;

    @Test
    void saveQuest_shouldReturnSavedQuestId() {
        // GIVEN
        var stubbedQuest = getStubbedQuest();
        var stubbedQuestEntity = getStubbedQuestEntity();
        // WHEN
        Mockito.when(questPersistenceMapper.toEntity(Mockito.any(Quest.class))).thenReturn(stubbedQuestEntity);
        Mockito.when(questRepository.save(stubbedQuestEntity)).thenReturn(stubbedQuestEntity);
        var savedQuestId = questPersistenceAdapter.saveQuest(stubbedQuest);
        // THEN
        assertEquals(stubbedQuestEntity.getId(), savedQuestId);
    }

    @Test
    void getQuest_shouldReturnQuest() {
        // GIVEN
        var stubbedQuestEntity = getStubbedQuestEntity();
        var id = (long) 1;
        var stubbedQuest = getStubbedQuest();
        // WHEN
        Mockito.when(questRepository.findQuestById(Mockito.anyLong())).thenReturn(stubbedQuestEntity);
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntity)).thenReturn(stubbedQuest);
        var quest = questPersistenceAdapter.getQuest(id);
        // THEN
        assertAll(
                () -> assertEquals(stubbedQuest.getQuestName(), quest.getQuestName()),
                () -> assertEquals(stubbedQuest.getRepoUrl(), quest.getRepoUrl()),
                () -> assertEquals(stubbedQuest.getStatus(), quest.getStatus()),
                () -> assertEquals(stubbedQuest.getDifficulty(), quest.getDifficulty()),
                () -> assertEquals(stubbedQuest.getContent(), quest.getContent()),
                () -> assertEquals(stubbedQuest.getUsers().size(), quest.getUsers().size())
        );
    }

    private QuestEntity getStubbedQuestEntity() {
        return new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
    }

    private Quest getStubbedQuest() {
        return Quest.builder().questName("Quest name").repoUrl("Repo url").status(QuestStatus.CREATED).difficulty(QuestDifficulty.BEGINNER).content("Content").users(new HashSet<>()).build();
    }

    private List<Quest> getStubbedQuestsList() {
        return List.of(
                Quest.builder().questName("1st Quest name").repoUrl("1st Repo url").status(QuestStatus.CREATED).difficulty(QuestDifficulty.BEGINNER).content("1st Content").users(new HashSet<>()).build(),
                Quest.builder().questName("2nd Quest  name").repoUrl("2nd Repo url").status(QuestStatus.STARTED).difficulty(QuestDifficulty.BEGINNER).content("2nd Content").users(new HashSet<>()).build(),
                Quest.builder().questName("3rd Quest name").repoUrl("3rd Repo url").status(QuestStatus.CREATED).difficulty(QuestDifficulty.MASTER).content("3rd Content").users(new HashSet<>()).build());
    }

}
