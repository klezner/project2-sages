package com.sages.project2.port.out;

import com.sages.project2.domain.model.Sample;

public interface SampleRepository {

    Sample getSample(Long id);
}
