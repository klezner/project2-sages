package com.sages.project2;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String saveUser(AppUser appUser);
}
