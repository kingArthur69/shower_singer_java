package com.amihaliov.shower_singer_java.service;

import com.amihaliov.shower_singer_java.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID userId);

    boolean existsById(UUID userId);

    User findUserByUserName(String userName);

    User saveUser(User user);

    User updateUser(User user, User newUser);

    void deleteUser(User user);

    void deleteUserById(UUID userId);

}
