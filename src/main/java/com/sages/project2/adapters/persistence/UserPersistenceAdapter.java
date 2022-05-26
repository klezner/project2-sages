package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.mappers.UserPersistenceMapper;
import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserPersistenceAdapter implements UserRepository{

    private final JpaUserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;


    @Override
    public Long saveUser(User user) {
        var savedUser =  userRepository.save(userPersistenceMapper.toEntity(user));
        return savedUser.getUserId();
    }
}
