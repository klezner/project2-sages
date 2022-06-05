package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.mappers.QuestPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@RequiredArgsConstructor
@Service
@Transactional(propagation = MANDATORY)
public class QuestPersistenceAdapter implements QuestRepository {

    private final JpaQuestRepository questRepository;
    private final QuestPersistenceMapper questMapper;

    @Override
    public Quest saveQuest(Quest quest) {
        var entity = questMapper.toEntity(quest);
        var savedQuest =  questRepository.save(entity);
        return questMapper.toDomain(savedQuest);
    }
}
