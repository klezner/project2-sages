package com.sages.project2.adapters.persistence.mappers;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.domain.models.Quest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestPersistenceMapper {

    QuestEntity toEntity(Quest quest);

    Quest toDomain(QuestEntity savedQuest);

    @IterableMapping(elementTargetType = Quest.class)
    List<Quest> toDomain(List<QuestEntity> entities);

}
