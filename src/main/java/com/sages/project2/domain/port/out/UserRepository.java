package com.sages.project2.domain.port.out;

import com.sages.project2.domain.model.User;

public interface UserRepository {

    Long saveUser(User user);

}
