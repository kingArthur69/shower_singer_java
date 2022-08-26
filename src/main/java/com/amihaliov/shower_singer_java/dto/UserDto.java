package com.amihaliov.shower_singer_java.dto;

import com.amihaliov.shower_singer_java.model.Role;
import com.amihaliov.shower_singer_java.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String userName;

    private String password;

    private boolean enabled = true;

    private boolean tokenExpired;

    private Collection<Role> roles;

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
//        userDto.setPassword(user.getPassword());
        userDto.setEnabled(user.isEnabled());
        userDto.setTokenExpired(user.isTokenExpired());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
