package com.sages.project2.domain.ports.in;

import com.sages.project2.domain.models.User;

public interface UserService {

    Long saveUser(User user);
}
