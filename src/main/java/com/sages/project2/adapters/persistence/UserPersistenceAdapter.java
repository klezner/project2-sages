package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository userRepository;

    @Override
    public boolean findByLogin(String login) {
        return userRepository.existsById(login);
    }
}
