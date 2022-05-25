package com.sages.project2;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public String saveUser(AppUser appUser) {
        userRepository.save(appUser);
        return null;
    }
}
