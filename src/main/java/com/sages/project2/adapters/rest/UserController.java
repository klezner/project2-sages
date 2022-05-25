package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.rest.dtos.UserDto;
import com.sages.project2.adapters.rest.mappers.UserRestMapper;
import com.sages.project2.domain.ports.in.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRestMapper userRestMapper;

    @PostMapping()
    public ResponseEntity<Long> saveUser(@RequestBody UserDto userDto){
        Long userId = userService.saveUser(userRestMapper.toDomain(userDto));
        return new ResponseEntity<>(userId,HttpStatus.OK);
    }
}
