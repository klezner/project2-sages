package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaQuestRepositoryTest {

    @Autowired
    private JpaQuestRepository questRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_shouldFindValidQuestById() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntity = questRepository.findQuestById(1L);
        assertAll(
                () -> assertEquals(foundEntity.getId(), questEntity.getId()),
                () -> assertEquals(foundEntity.getQuestName(), questEntity.getQuestName()),
                () -> assertEquals(foundEntity.getRepoUrl(), questEntity.getRepoUrl()),
                () -> assertEquals(foundEntity.getStatus(), questEntity.getStatus()),
                () -> assertEquals(foundEntity.getDifficulty(), questEntity.getDifficulty()),
                () -> assertEquals(foundEntity.getContent(), questEntity.getContent()),
                () -> assertEquals(foundEntity.getUsers().size(), questEntity.getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsEmpty_shouldNotFindValidQuestByIdAndReturnNull() {
        assertTrue(questRepository.findAll().isEmpty());
        assertNull(questRepository.findQuestById(1L));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_statusIsValid_shouldFindValidQuestByStatus() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByStatus(QuestStatus.CREATED);
        assertAll(
                () -> assertEquals(1, foundEntities.size()),
                () -> assertEquals(foundEntities.get(0).getId(), questEntity.getId()),
                () -> assertEquals(foundEntities.get(0).getQuestName(), questEntity.getQuestName()),
                () -> assertEquals(foundEntities.get(0).getRepoUrl(), questEntity.getRepoUrl()),
                () -> assertEquals(foundEntities.get(0).getStatus(), questEntity.getStatus()),
                () -> assertEquals(foundEntities.get(0).getDifficulty(), questEntity.getDifficulty()),
                () -> assertEquals(foundEntities.get(0).getContent(), questEntity.getContent()),
                () -> assertEquals(foundEntities.get(0).getUsers().size(), questEntity.getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_statusIsValidButNotInDb_shouldNotFindQuestByStatus() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByStatus(QuestStatus.STARTED);
        assertEquals(0, foundEntities.size());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_difficultyIsValid_shouldFindValidQuestByDifficulty() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByDifficulty(QuestDifficulty.BEGINNER);
        assertAll(
                () -> assertEquals(1, foundEntities.size()),
                () -> assertEquals(foundEntities.get(0).getId(), questEntity.getId()),
                () -> assertEquals(foundEntities.get(0).getQuestName(), questEntity.getQuestName()),
                () -> assertEquals(foundEntities.get(0).getRepoUrl(), questEntity.getRepoUrl()),
                () -> assertEquals(foundEntities.get(0).getStatus(), questEntity.getStatus()),
                () -> assertEquals(foundEntities.get(0).getDifficulty(), questEntity.getDifficulty()),
                () -> assertEquals(foundEntities.get(0).getContent(), questEntity.getContent()),
                () -> assertEquals(foundEntities.get(0).getUsers().size(), questEntity.getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_difficultyIsValidButNotInDb_shouldNotFindQuestByDifficulty() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByDifficulty(QuestDifficulty.MASTER);
        assertEquals(0, foundEntities.size());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_difficultyAndStatusAreValid_shouldFindValidQuestByDifficultyAndStatus() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByDifficultyAndStatus(QuestDifficulty.BEGINNER, QuestStatus.CREATED);
        assertAll(
                () -> assertEquals(1, foundEntities.size()),
                () -> assertEquals(foundEntities.get(0).getId(), questEntity.getId()),
                () -> assertEquals(foundEntities.get(0).getQuestName(), questEntity.getQuestName()),
                () -> assertEquals(foundEntities.get(0).getRepoUrl(), questEntity.getRepoUrl()),
                () -> assertEquals(foundEntities.get(0).getStatus(), questEntity.getStatus()),
                () -> assertEquals(foundEntities.get(0).getDifficulty(), questEntity.getDifficulty()),
                () -> assertEquals(foundEntities.get(0).getContent(), questEntity.getContent()),
                () -> assertEquals(foundEntities.get(0).getUsers().size(), questEntity.getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_difficultyAndStatusAreValidButNotInDb_shouldNotFindQuestByDifficultyAndStatus() {
        assertTrue(questRepository.findAll().isEmpty());
        var questEntity = new QuestEntity(1L, "Quest name", "Repo url", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "Content", new HashSet<>());
        questRepository.save(questEntity);
        assertEquals(1, questRepository.findAll().size());
        var foundEntities = questRepository.findAllByDifficultyAndStatus(QuestDifficulty.MASTER, QuestStatus.COMPLETED);
        assertEquals(0, foundEntities.size());
    }

}
