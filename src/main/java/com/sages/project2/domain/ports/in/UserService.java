package com.sages.project2.domain.ports.in;

import com.sages.project2.domain.model.User;

public interface UserService {

    Long saveUser(User user);
}
