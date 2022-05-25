package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.model.User;

public interface UserRepository {

    Long saveUser(User user);

}
