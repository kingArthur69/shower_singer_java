package com.amihaliov.shower_singer_java.controller;

import com.amihaliov.shower_singer_java.dto.UserDto;
import com.amihaliov.shower_singer_java.model.User;
import com.amihaliov.shower_singer_java.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Object> getUser(@PathVariable String userName) {
        try {
            User user = userService.findUserByUserName(userName);
            return ResponseEntity.ok().body(UserDto.fromUser(user));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.saveUser(User.fromUserDto(userDto));
            return ResponseEntity.ok().body(user.getId().toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        try {
            User newUser = User.fromUserDto(userDto);
            User updatedUser = userService.findById(id)
                    .map(user -> userService.updateUser(user, newUser))
                    .orElseGet(() -> userService.saveUser(newUser));
            return ResponseEntity.ok().body(updatedUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUserById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
