package com.amihaliov.shower_singer_java.dto;

import com.amihaliov.shower_singer_java.model.Role;
import com.amihaliov.shower_singer_java.model.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private static final String USER_NAME = "testUserName";
    private static final String PASSWORD = "testPassword";
    private static final List<Role> ROLES = Collections.singletonList(new Role("ROLE_ADMIN"));

    @Test
    void fromUser() {
        UUID uuid = UUID.randomUUID();

        User user = new User();
        user.setId(uuid);
        user.setUserName(USER_NAME);
        user.setPassword(PASSWORD);
        user.setEnabled(true);
        user.setTokenExpired(true);
        user.setRoles(ROLES);

        UserDto userDto = UserDto.fromUser(user);

        assertEquals(uuid, userDto.getId());
        assertEquals(USER_NAME, userDto.getUserName());
        assertEquals(null, userDto.getPassword());
        assertTrue(userDto.isEnabled());
        assertTrue(userDto.isTokenExpired());
        assertEquals(ROLES, userDto.getRoles());
    }
}