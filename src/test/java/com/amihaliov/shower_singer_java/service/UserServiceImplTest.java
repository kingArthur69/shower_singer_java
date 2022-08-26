package com.amihaliov.shower_singer_java.service;

import com.amihaliov.shower_singer_java.model.Role;
import com.amihaliov.shower_singer_java.model.User;
import com.amihaliov.shower_singer_java.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImplTest {

    @Autowired
    UserRepository repository;

    @Autowired
    @Qualifier("userService")
    UserService service;

    @BeforeEach
    void setUp() {
//        repository.deleteAll();
    }

    @Test
    void findById() {
        User save = repository.save(new User());

        assertTrue(service.findById(save.getId()).isPresent());
    }

    @Test
    void existsById() {
        User save = repository.save(new User());

        assertTrue(service.existsById(save.getId()));
    }

    @Test
    void findUserByUserName() {
        User entity = new User();
        entity.setUserName("testUserName");
        repository.save(entity);

        User user = service.findUserByUserName("testUserName");

        assertNotNull(user);
        assertEquals("testUserName", user.getUserName());
    }

    @Test
    void findUserByUserName_UserNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.findUserByUserName("testUserName"));
    }

    @Test
    void saveUser() {
        User save = service.saveUser(new User());

        assertNotNull(save.getId());
    }

    @Test
    void updateUser() {
        User entity = new User();
        entity.setUserName("testUserName");
        entity.setPassword("testPassword");
        entity.setEnabled(true);
        entity.setTokenExpired(true);
        entity.setRoles(Collections.singletonList(new Role("ADMIN")));
        repository.save(entity);

        User newUser = new User();
        newUser.setEnabled(false);
        newUser.setPassword("test");
        User user = service.updateUser(entity, newUser);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("testUserName", user.getUserName());
        assertNotNull(user.getPassword());
        assertFalse(user.isEnabled());
        assertFalse(user.isTokenExpired());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    void deleteUser() {
        User save = repository.save(new User());

        service.deleteUser(save);

        assertFalse(repository.findById(save.getId()).isPresent());
    }

    @Test
    void deleteUserById() {
        User save = repository.save(new User());

        service.deleteUserById(save.getId());

        assertFalse(repository.findById(save.getId()).isPresent());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return new UserServiceImpl(userRepository, passwordEncoder);
        }
    }
}