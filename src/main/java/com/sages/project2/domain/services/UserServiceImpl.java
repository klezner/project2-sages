package com.sages.project2.domain.services;

import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.in.UserService;
import com.sages.project2.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long saveUser(User user) {
        return userRepository.saveUser(user);
    }
}
