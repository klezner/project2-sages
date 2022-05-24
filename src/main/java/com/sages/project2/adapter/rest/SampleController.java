package com.sages.project2.adapter.rest;

import com.sages.project2.adapter.rest.dto.SampleDto;
import com.sages.project2.adapter.rest.mapper.RestSampleMapper;
import com.sages.project2.domain.model.Sample;
import com.sages.project2.domain.port.in.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SampleController {

    private final SampleService service;
    private final RestSampleMapper restSampleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SampleDto> getSample(@PathVariable Long id){
        var sample = service.getSample(id);
        return new ResponseEntity<>(restSampleMapper.toDto(sample), HttpStatus.OK);
    }
}
