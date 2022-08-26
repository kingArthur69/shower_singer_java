package com.amihaliov.shower_singer_java.repository;

import com.amihaliov.shower_singer_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String userName);
}
