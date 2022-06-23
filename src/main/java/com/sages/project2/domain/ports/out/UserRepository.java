package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.models.User;

public interface UserRepository {

    User findByLogin(String login);
}
