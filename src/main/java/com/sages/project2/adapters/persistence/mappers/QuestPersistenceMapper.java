package com.sages.project2.adapters.persistence.mappers;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestPersistenceMapper {

    QuestEntity toEntity(Quest quest);
}
