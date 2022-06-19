package com.sages.project2.adapters.rest.mappers;

import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.adapters.rest.dtos.SolutionDto;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.Solution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolutionRestMapper {

    Solution toDomain(SolutionDto solutionDto);

    SolutionDto toDto(Solution solution);

}
