package com.sages.project2.adapter.rest.mapper;

import com.sages.project2.adapter.rest.dto.SampleDto;
import com.sages.project2.domain.model.Sample;

// @Mapper (mapstruct)
public interface RestSampleMapper {

    SampleDto toDto(Sample sample);
    Sample toDomain(SampleDto sampleDto);
}
