package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.adapters.rest.mappers.UserRestMapper;
import com.sages.project2.domain.models.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private UserRestMapper userRestMapper;
    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    // TODO: fix
//    @Test
//    void findByLogin_shouldReturnUser() {
//        // GIVEN
//        var stubbedUserEntity = getStubbedUserEntity();
//        var stubbedUser = getStubbedUser();
//        var login = "Johnny";
//        // WHEN
//        Mockito.when(userRepository.findByLogin(login)).thenReturn(stubbedUserEntity);
//        Mockito.when(userRestMapper.toDomain(stubbedUserEntity)).thenReturn(stubbedUser);
//        var user = userPersistenceAdapter.findById(login);
//        // THEN
//        assertEquals(login, user.getLogin());
//    }

    private UserEntity getStubbedUserEntity() {
        return new UserEntity("Johnny", "ROLE_USER", new HashSet<>());
    }

    private User getStubbedUser() {
        var stubbedUser = new User();
        stubbedUser.setLogin("Johnny");
        return stubbedUser;
    }

}
