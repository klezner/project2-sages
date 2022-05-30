package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.rest.dtos.UserDto;
import com.sages.project2.adapters.rest.mappers.UserRestMapper;
import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {

    private static final String FAILED = "failed";
    private static final String PASSED = "passed";
    private final QuestService questService;


    @GetMapping("/{userId}/{questId}")
    public ResponseEntity<String> checkQuest(@PathVariable Long userId, @PathVariable Long questId){
        boolean isCorrect = questService.check(userId, questId);
        String status = isCorrect ? PASSED : FAILED;

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
