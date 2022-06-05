package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.rest.mappers.QuestRestMapper;
import com.sages.project2.adapters.rest.dtos.QuestDto;

import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.in.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;
    private final QuestRestMapper questRestMapper;

    @PostMapping("/admin")
    public ResponseEntity<Long> saveQuest(@RequestBody QuestDto questDto) throws IOException {
        var quest = questService.saveQuest(questRestMapper.toDomain(questDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(quest.getId());
    }
}













