package com.sages.project2.port.adapter.persistence;

import com.sages.project2.port.adapter.persistence.entity.SampleEntity;
import com.sages.project2.port.adapter.persistence.mapper.PersistenceSampleMapper;
import com.sages.project2.port.adapter.persistence.repository.SampleRepo;
import com.sages.project2.domain.model.Sample;
import com.sages.project2.port.out.SampleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampleDbAdapter implements SampleRepository {

    private final SampleRepo sampleRepo;
    private final PersistenceSampleMapper persistenceSampleMapper;

    @Override
    public Sample getSample(Long id) {
        SampleEntity sampleEntity =  sampleRepo.getSample(id);
        return persistenceSampleMapper.toDomain(sampleEntity);
    }
}
