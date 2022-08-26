package com.amihaliov.shower_singer_java.model;

import com.amihaliov.shower_singer_java.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final String USER_NAME = "testUserName";
    private static final String PASSWORD = "testPassword";
    public static final List<Role> ROLES = Collections.singletonList(new Role("ROLE_ADMIN"));

    @Test
    void fromUserDto() {
        UUID uuid = UUID.randomUUID();

        UserDto userDto = new UserDto();
        userDto.setId(uuid);
        userDto.setUserName(USER_NAME);
        userDto.setPassword(PASSWORD);
        userDto.setEnabled(true);
        userDto.setTokenExpired(true);
        userDto.setRoles(ROLES);

        User user = User.fromUserDto(userDto);

        assertEquals(uuid, user.getId());
        assertEquals(USER_NAME, user.getUserName());
        assertEquals(PASSWORD, user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isTokenExpired());
        assertEquals(ROLES, user.getRoles());
    }
}