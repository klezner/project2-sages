package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.exception.QuestNotFoundException;
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

import java.util.Optional;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    void getQuest_shouldReturnQuest() throws QuestNotFoundException {
        // GIVEN
        var stubbedQuestEntity = getStubbedQuestEntity();
        var id = (long) 1;
        var stubbedQuest = getStubbedQuest();
        // WHEN
        Mockito.when(questRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stubbedQuestEntity));
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

    @Test
    void findAllQuests_shouldReturnQuestsList() {
        // GIVEN
        var stubbedQuestEntitiesList = getStubbedQuestEntitiesList();
        var stubbedQuestsList = getStubbedQuestsList();
        // WHEN
        Mockito.when(questRepository.findAll()).thenReturn(stubbedQuestEntitiesList);
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntitiesList)).thenReturn(stubbedQuestsList);
        var quests = questPersistenceAdapter.findAllQuests();
        // THEN
        assertAll(
                () -> assertEquals(3, quests.size()),
                () -> assertEquals(stubbedQuestsList.get(0).getQuestName(), quests.get(0).getQuestName()),
                () -> assertEquals(stubbedQuestsList.get(0).getRepoUrl(), quests.get(0).getRepoUrl()),
                () -> assertEquals(stubbedQuestsList.get(0).getStatus(), quests.get(0).getStatus()),
                () -> assertEquals(stubbedQuestsList.get(0).getDifficulty(), quests.get(0).getDifficulty()),
                () -> assertEquals(stubbedQuestsList.get(0).getContent(), quests.get(0).getContent()),
                () -> assertEquals(stubbedQuestsList.get(0).getUsers().size(), quests.get(0).getUsers().size()),
                () -> assertEquals(stubbedQuestsList.get(1).getQuestName(), quests.get(1).getQuestName()),
                () -> assertEquals(stubbedQuestsList.get(1).getRepoUrl(), quests.get(1).getRepoUrl()),
                () -> assertEquals(stubbedQuestsList.get(1).getStatus(), quests.get(1).getStatus()),
                () -> assertEquals(stubbedQuestsList.get(1).getDifficulty(), quests.get(1).getDifficulty()),
                () -> assertEquals(stubbedQuestsList.get(1).getContent(), quests.get(1).getContent()),
                () -> assertEquals(stubbedQuestsList.get(1).getUsers().size(), quests.get(1).getUsers().size()),
                () -> assertEquals(stubbedQuestsList.get(2).getQuestName(), quests.get(2).getQuestName()),
                () -> assertEquals(stubbedQuestsList.get(2).getRepoUrl(), quests.get(2).getRepoUrl()),
                () -> assertEquals(stubbedQuestsList.get(2).getStatus(), quests.get(2).getStatus()),
                () -> assertEquals(stubbedQuestsList.get(2).getDifficulty(), quests.get(2).getDifficulty()),
                () -> assertEquals(stubbedQuestsList.get(2).getContent(), quests.get(2).getContent()),
                () -> assertEquals(stubbedQuestsList.get(2).getUsers().size(), quests.get(2).getUsers().size())
        );
    }

    @Test
    void findAllQuestsByStatus_shouldReturnQuestsListWithStatusCreated() {
        // GIVEN
        var status = QuestStatus.CREATED;
        var stubbedQuestEntitiesList = getStubbedQuestEntitiesList().stream().filter(questEntity -> status.equals(questEntity.getStatus())).collect(Collectors.toList());
        var stubbedQuestsList = getStubbedQuestsList().stream().filter(questEntity -> status.equals(questEntity.getStatus())).collect(Collectors.toList());
        // WHEN
        Mockito.when(questRepository.findAllByStatus(status)).thenReturn(stubbedQuestEntitiesList);
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntitiesList)).thenReturn(stubbedQuestsList);
        var quests = questPersistenceAdapter.findAllQuestsByStatus(status);
        // THEN
        assertAll(
                () -> assertEquals(2, quests.size()),
                () -> assertEquals(stubbedQuestsList.get(0).getStatus(), quests.get(0).getStatus()),
                () -> assertEquals(stubbedQuestsList.get(1).getStatus(), quests.get(1).getStatus())
        );
    }

    @Test
    void findAllQuestsByDifficulty_shouldReturnQuestsListWithDifficultyBeginner() {
        // GIVEN
        var difficulty = QuestDifficulty.BEGINNER;
        var stubbedQuestEntitiesList = getStubbedQuestEntitiesList().stream().filter(questEntity -> difficulty.equals(questEntity.getDifficulty())).collect(Collectors.toList());
        var stubbedQuestsList = getStubbedQuestsList().stream().filter(questEntity -> difficulty.equals(questEntity.getDifficulty())).collect(Collectors.toList());
        // WHEN
        Mockito.when(questRepository.findAllByDifficulty(difficulty)).thenReturn(stubbedQuestEntitiesList);
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntitiesList)).thenReturn(stubbedQuestsList);
        var quests = questPersistenceAdapter.findAllQuestsByDifficulty(difficulty);
        // THEN
        assertAll(
                () -> assertEquals(2, quests.size()),
                () -> assertEquals(difficulty, quests.get(0).getDifficulty()),
                () -> assertEquals(difficulty, quests.get(1).getDifficulty())
        );
    }

    @Test
    void findAllQuestsByDifficultyAndStatus_shouldReturnQuestsListWithGivenDifficultyAndStatus() {
        // GIVEN
        var difficulty = QuestDifficulty.BEGINNER;
        var status = QuestStatus.CREATED;

        var stubbedQuestEntitiesList = getStubbedQuestEntitiesList()
                .stream()
                .filter(questEntity -> difficulty.equals(questEntity.getDifficulty()))
                .filter(questEntity -> status.equals(questEntity.getStatus()))
                .collect(Collectors.toList());

        var stubbedQuestsList = getStubbedQuestsList()
                .stream()
                .filter(quest -> difficulty.equals(quest.getDifficulty()))
                .filter(quest -> status.equals(quest.getStatus()))
                .collect(Collectors.toList());
        // WHEN
        Mockito.when(questRepository.findAllByDifficulty(difficulty)).thenReturn(stubbedQuestEntitiesList);
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntitiesList)).thenReturn(stubbedQuestsList);
        var quests = questPersistenceAdapter.findAllQuestsByDifficulty(difficulty);

    }


    @Test
    void findById_shouldReturnOptionalOfQuest() {
        // GIVEN
        var stubbedQuestEntity = getStubbedQuestEntity();
        var id = 1L;
        var stubbedQuest = getStubbedQuest();
        // WHEN
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(stubbedQuestEntity));
        Mockito.when(questPersistenceMapper.toDomain(stubbedQuestEntity)).thenReturn(stubbedQuest);
        var quest = questPersistenceAdapter.findById(id);
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

    private List<QuestEntity> getStubbedQuestEntitiesList() {
        return List.of(
                new QuestEntity(1L, "1st Quest name", "1st Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "1st Content", new HashSet<>()),
                new QuestEntity(2L, "2nd Quest name", "2nd Repo url", QuestStatus.STARTED, QuestDifficulty.BEGINNER, "2nd Content", new HashSet<>()),
                new QuestEntity(3L, "3rd Quest name", "3rd Repo url", QuestStatus.CREATED, QuestDifficulty.MASTER, "3rd Content", new HashSet<>())
        );
    }

    private List<Quest> getStubbedQuestsList() {
        return List.of(
                Quest.builder().questName("1st Quest name").repoUrl("1st Repo url").status(QuestStatus.CREATED).difficulty(QuestDifficulty.BEGINNER).content("1st Content").users(new HashSet<>()).build(),
                Quest.builder().questName("2nd Quest  name").repoUrl("2nd Repo url").status(QuestStatus.STARTED).difficulty(QuestDifficulty.BEGINNER).content("2nd Content").users(new HashSet<>()).build(),
                Quest.builder().questName("3rd Quest name").repoUrl("3rd Repo url").status(QuestStatus.CREATED).difficulty(QuestDifficulty.MASTER).content("3rd Content").users(new HashSet<>()).build());
    }
}


