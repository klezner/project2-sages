package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.rest.mappers.QuestRestMapper;
import com.sages.project2.adapters.rest.dtos.QuestDto;

import com.sages.project2.domain.exceptions.RepositoryAlreadyExistsException;
import com.sages.project2.domain.ports.in.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;
    private final QuestRestMapper questRestMapper;


    @PostMapping("/admin")
    public ResponseEntity<Long> saveQuest(@RequestBody QuestDto questDto) throws IOException {
        Long questId;
        try {
            questId = questService.saveQuest(questRestMapper.toDomain(questDto));
        } catch (RepositoryAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(questId);
    }


}













