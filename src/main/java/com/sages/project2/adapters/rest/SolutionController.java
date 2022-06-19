package com.sages.project2.adapters.rest;


import com.sages.project2.adapters.rest.dtos.SolutionDto;
import com.sages.project2.adapters.rest.mappers.SolutionRestMapper;
import com.sages.project2.domain.ports.in.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/solutions")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;
    private final SolutionRestMapper mapper;

    @PostMapping
    public ResponseEntity<SolutionDto> addSolution(@RequestBody SolutionDto solutionDto) throws IOException {
        var solution = mapper.toDomain(solutionDto);
        var solutionFromDb = solutionService.addSolution(solution);
        var solutionDtoFromDb = mapper.toDto(solutionFromDb);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(solutionDtoFromDb);
    }

}
