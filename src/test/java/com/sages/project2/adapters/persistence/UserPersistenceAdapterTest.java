package com.sages.project2.adapters.persistence;

import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.adapters.rest.mappers.UserRestMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private UserRestMapper userRestMapper;
    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

//    @Test
//    void a() {
//        var stubbedUserEntity = new UserEntity("Johnny", "ROLE_USER", new HashSet<>());
//        var stubbedUser = new User();
//        var login = "Johnny";
//        stubbedUser.setLogin(login);
//
//        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenReturn(stubbedUserEntity);
//        Mockito.when(userRestMapper.toDomain(stubbedUserEntity)).thenReturn(stubbedUser);
//        var user = userPersistenceAdapter.findById("Johnny");
//
//        assertEquals(login, user.getLogin());
//    }

}
