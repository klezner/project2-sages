package com.sages.project2.adapters.rest.mappers;

import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.domain.models.Quest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestRestMapper {
    //
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "status", ignore = true)
    Quest toDomain(QuestDto questDto);

    QuestDto toDto(Quest quest);

    @IterableMapping(elementTargetType = QuestDto.class)
    List<QuestDto> toDto(List<Quest> quests);

}
