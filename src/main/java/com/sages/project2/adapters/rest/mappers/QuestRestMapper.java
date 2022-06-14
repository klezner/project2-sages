package com.sages.project2.adapters.rest.mappers;

import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.domain.models.Quest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestRestMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "status", ignore = true)
    Quest toDomain(QuestDto questDto);

    QuestDto toDto(Quest quest);

}
