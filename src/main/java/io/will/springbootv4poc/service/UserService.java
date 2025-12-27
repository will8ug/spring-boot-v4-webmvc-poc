package io.will.springbootv4poc.service;

import io.will.springbootv4poc.entity.User;
import io.will.springbootv4poc.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
}

