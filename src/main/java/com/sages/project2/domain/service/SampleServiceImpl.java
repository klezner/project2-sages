package com.sages.project2.domain.service;

import com.sages.project2.domain.model.Sample;
import com.sages.project2.port.in.SampleService;
import com.sages.project2.port.out.SampleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;

    @Override
    public Sample getSample(Long id) {
        return sampleRepository.getSample(id);
    }
}
