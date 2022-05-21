package com.sages.project2.port.adapter.persistence.mapper;

import com.sages.project2.port.adapter.persistence.entity.SampleEntity;
import com.sages.project2.domain.model.Sample;

public interface PersistenceSampleMapper {

    SampleEntity toEntity(Sample sample);
    Sample toDomain(SampleEntity sampleEntity);
}
