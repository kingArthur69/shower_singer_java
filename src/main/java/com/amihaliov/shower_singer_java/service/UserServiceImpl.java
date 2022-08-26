package com.amihaliov.shower_singer_java.service;

import com.amihaliov.shower_singer_java.model.User;
import com.amihaliov.shower_singer_java.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find User: " + userName));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, User newUser) {
        try {
            user.setRoles(newUser.getRoles());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setEnabled(newUser.isEnabled());
            user.setTokenExpired(newUser.isTokenExpired());
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("User does not exist for id: " + newUser.getId());
        }
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }
}
