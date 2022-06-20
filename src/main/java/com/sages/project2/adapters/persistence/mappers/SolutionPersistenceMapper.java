package com.sages.project2.adapters.persistence.mappers;

import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.entities.SolutionEntity;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.Solution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolutionPersistenceMapper {

    SolutionEntity toEntity(Solution quest);

    Solution toDomain(SolutionEntity savedQuest);

}
