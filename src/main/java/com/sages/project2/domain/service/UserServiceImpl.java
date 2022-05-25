package com.sages.project2.domain.service;

import com.sages.project2.domain.model.User;
import com.sages.project2.domain.port.in.UserService;
import com.sages.project2.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long saveUser(User user) {
        return userRepository.saveUser(user);
    }
}
