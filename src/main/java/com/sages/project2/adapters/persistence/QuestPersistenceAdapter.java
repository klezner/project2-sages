package com.sages.project2.adapters.persistence;


import com.sages.project2.adapters.exception.QuestNotFoundException;
import com.sages.project2.adapters.persistence.mappers.QuestPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@RequiredArgsConstructor
@Service
@Transactional(propagation = MANDATORY)
public class QuestPersistenceAdapter implements QuestRepository {

    private final JpaQuestRepository questRepository;
    private final QuestPersistenceMapper questMapper;

    @Override
    public Long saveQuest(Quest quest) {
        var entity = questMapper.toEntity(quest);
        return questRepository.save(entity).getId();
    }

    @Override
    public Quest getQuest(Long id) {
        var questEntity = questRepository.findById(id);
        if (questEntity.isPresent()) {
            return questMapper.toDomain(questEntity.get());
        } else {
            // UWAGA ZMIENIĆ TEN EXCEPTION
            throw new RuntimeException();
        }
    }

    public List<Quest> findAllQuests() {
        var entities = questRepository.findAll();
        return questMapper.toDomain(entities);
    }

    @Override
    public List<Quest> findAllQuestsByDifficulty(QuestDifficulty difficulty) {
        var entities = questRepository.findAllByDifficulty(difficulty);
        return questMapper.toDomain(entities);
    }

    @Override
    public List<Quest> findAllQuestsByStatus(QuestStatus status) {
        var entities = questRepository.findAllByStatus(status);
        return questMapper.toDomain(entities);
    }

    @Override
    public List<Quest> findAllQuestsByDifficultyAndStatus(QuestDifficulty difficulty, QuestStatus status) {
        var entities = questRepository.findAllByDifficultyAndStatus(difficulty, status);
        return questMapper.toDomain(entities);
    }

    @Override
    public Quest findById(Long questId) {
        return questRepository.findById(questId)
                .map(questMapper::toDomain)
                .orElseThrow(() -> new QuestNotFoundException("Quest with id: " + questId + " does not exist."));
    }

}
