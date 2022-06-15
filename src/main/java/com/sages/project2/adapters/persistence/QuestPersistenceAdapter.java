package com.sages.project2.adapters.persistence;


import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.mappers.QuestPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.out.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        var quest = questRepository.findQuestById(id);
        return questRepository.toDomain(quest);
    }
}
