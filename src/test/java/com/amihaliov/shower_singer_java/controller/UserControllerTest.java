package com.amihaliov.shower_singer_java.controller;

import com.amihaliov.shower_singer_java.dto.UserDto;
import com.amihaliov.shower_singer_java.model.Role;
import com.amihaliov.shower_singer_java.model.User;
import com.amihaliov.shower_singer_java.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
class UserControllerTest {

    private static final String USER_NAME = "testUserName";
    private static final String PASSWORD = "testPassword";
    private static final List<Role> ROLE_USER = Collections.singletonList(new Role("ROLE_USER"));
    private static final UUID ID = UUID.randomUUID();
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void getUser() throws Exception {
        User user = new User();
        user.setId(ID);
        user.setUserName(USER_NAME);
        user.setPassword(PASSWORD);
        user.setEnabled(true);
        user.setTokenExpired(false);
        user.setRoles(ROLE_USER);

        when(userService.findUserByUserName(USER_NAME)).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/users/testUserName"))
                .andExpect(status().isOk()).andReturn();

        UserDto userDto = new UserDto();
        userDto.setId(ID);
        userDto.setUserName(USER_NAME);
        userDto.setEnabled(true);
        userDto.setRoles(ROLE_USER);

        assertEquals(objectMapper.writeValueAsString(userDto), result.getResponse().getContentAsString());
    }

    @Test
    void addNewUser() throws Exception {
        User user = new User();
        user.setId(ID);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        UserDto userDto = new UserDto();

        MvcResult result = mockMvc.perform(
                        post("/users/").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(ID.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void editUser_userExists() throws Exception {
        when(userService.findById(ID)).thenReturn(Optional.of(new User()));
        when(userService.updateUser(any(User.class), any(User.class))).thenReturn(new User());

        MvcResult result = mockMvc.perform(
                        put("/users/" + ID).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new UserDto())))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, never()).saveUser(any());

        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void editUser_userNotExists() throws Exception {
        when(userService.findById(ID)).thenReturn(Optional.empty());
        when(userService.saveUser(any(User.class))).thenReturn(new User());

        MvcResult result = mockMvc.perform(
                        put("/users/" + ID).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new UserDto())))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, never()).updateUser(any(),any());

        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + ID).with(csrf()))
                .andExpect(status().isOk());

        verify(userService).deleteUserById(ID);
    }
}