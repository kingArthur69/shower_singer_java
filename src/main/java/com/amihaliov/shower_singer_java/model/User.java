package com.amihaliov.shower_singer_java.model;

import com.amihaliov.shower_singer_java.dto.UserDto;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "user_id", columnDefinition = "char(36)")
    private UUID id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @Column(name = "token_expired")
    private boolean tokenExpired;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new ArrayList<>();

    public static User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        user.setTokenExpired(userDto.isTokenExpired());
        user.setRoles(userDto.getRoles());
        return user;
    }
}
