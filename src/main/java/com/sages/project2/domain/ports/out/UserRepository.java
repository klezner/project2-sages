package com.sages.project2.domain.ports.out;

public interface UserRepository {

    boolean findByLogin(String login);
}
