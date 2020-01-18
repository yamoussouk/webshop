package com.example.backend.service;

import com.example.backend.command.UserCommand;
import com.example.backend.dto.UserRegistrationDto;
import com.example.backend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {

    Set<User> getUsers();

    User findById(Long id);

    UserCommand saveUserCommand(UserCommand command);

    UserCommand findCommandById(Long l);

    void deleteById(Long idToDelete);

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    void updatePassword(String password, Long userId);

    void registerUser(User registerRequest) ;

    void saveUser(User user);
}

