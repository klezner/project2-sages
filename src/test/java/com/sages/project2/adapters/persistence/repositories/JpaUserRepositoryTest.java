package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository userRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsPopulated_shouldFindValidUserByLogin() {
        assertTrue(userRepository.findAll().isEmpty());
        var userEntity = new UserEntity("Johnny", "ROLE_USER", new HashSet<>());
        userRepository.save(userEntity);
        assertEquals(1, userRepository.findAll().size());
        var foundEntityOptional = userRepository.findByLogin("Johnny");
        var foundEntity = foundEntityOptional.get();
        assertAll(
                () -> assertEquals(foundEntity.getLogin(), userEntity.getLogin()),
                () -> assertEquals(foundEntity.getRole(), userEntity.getRole()),
                () -> assertEquals(foundEntity.getQuests().size(), userEntity.getQuests().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void dbIsEmpty_shouldFindEmptyOptionalOfUser() {
        assertTrue(userRepository.findAll().isEmpty());
        assertTrue(userRepository.findByLogin("Johnny").isEmpty());
    }

}
