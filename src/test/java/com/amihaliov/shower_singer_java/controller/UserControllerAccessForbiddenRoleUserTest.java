package com.amihaliov.shower_singer_java.controller;

import com.amihaliov.shower_singer_java.dto.UserDto;
import com.amihaliov.shower_singer_java.repository.UserRepository;
import com.amihaliov.shower_singer_java.security.MyUserDetailsService;
import com.amihaliov.shower_singer_java.security.SecurityConfig;
import com.amihaliov.shower_singer_java.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerAccessForbiddenRoleUserTest {

    private static final UUID ID = UUID.randomUUID();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users/testUserName"))
                .andExpect(status().isForbidden());
    }

    @Test
    void addNewUser() throws Exception {
        mockMvc.perform(post("/users/").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDto())))
                .andExpect(status().isForbidden());
    }

    @Test
    void editUser() throws Exception {
        mockMvc.perform(put("/users/" + ID).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDto())))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + ID).with(csrf()))
                .andExpect(status().isForbidden());
    }
}