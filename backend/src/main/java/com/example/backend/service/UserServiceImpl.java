package com.example.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backend.command.UserCommand;
import com.example.backend.converter.UserCommandToUser;
import com.example.backend.converter.UserToUserCommand;
import com.example.backend.dto.UserRegistrationDto;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.*;
import com.example.backend.repository.AuthorityRepository;
import com.example.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Qualifier("original")
public class UserServiceImpl implements UserService {

    private final Long ADMIN = 1L;
    private final Long USER = 2L;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    private final UserRepository userRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> allUsers = new HashSet<>();
        this.userRepository.findAll().iterator().forEachRemaining(allUsers::add);
        return allUsers;
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with given id is not found");
        }
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand command) {
        User detachedUser = userCommandToUser.convert(command);

        User savedUser = userRepository.save(detachedUser);
        log.debug("Saved UserId:" + savedUser.getId());
        return userToUserCommand.convert(savedUser);
    }

    @Override
    @Transactional
    public UserCommand findCommandById(Long l) {
        return userToUserCommand.convert(findById(l));
    }

    @Override
    public void deleteById(Long idToDelete) {
        userRepository.deleteById(idToDelete);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setFirstname(registration.getFirstName());
        user.setLastname(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPasswd(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }

    @Override
    public void registerUser(User registerRequest) {
        Authority user = authorityRepository.findById(USER).get();
        registerRequest.setAuthority(user);
        registerRequest.setEnabled(false);
        registerRequest.setPasswd(passwordEncoder.encode(registerRequest.getPasswd()));
        userRepository.save(registerRequest);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPasswd(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

