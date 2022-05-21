package com.sages.project2.port.adapter.rest;

import com.sages.project2.domain.model.Sample;
import com.sages.project2.port.in.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SampleController {

    private final SampleService service;

    //endpoint
    public ResponseEntity<Sample> getSample(Long id){
        //service.getSample(id);
        return null;
    }
}
