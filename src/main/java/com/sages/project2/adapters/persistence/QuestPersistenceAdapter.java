package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.mappers.QuestPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class QuestPersistenceAdapter implements QuestRepository {

    private final JpaQuestRepository questRepository;
    private final QuestPersistenceMapper questPersistenceMapper;

    @Override
    public Long save(Quest quest) {
        var questEntity = questPersistenceMapper.toEntity(quest);
        var savedQuest = questRepository.save(questEntity);
        return savedQuest.getQuestId();
    }
}
