package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.adapters.rest.mappers.UserRestMapper;
import com.sages.project2.domain.models.User;
import com.sages.project2.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository userRepository;
    private final UserRestMapper userRestMapper;

    @Override
    public User findByLogin(String login) {
        var userEntity = userRepository.findByLogin(login);
        return userRestMapper.toDomain(userEntity);

    }
}
