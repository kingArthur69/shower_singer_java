package com.amihaliov.shower_singer_java.bootsrap;

import com.amihaliov.shower_singer_java.model.Role;
import com.amihaliov.shower_singer_java.model.User;
import com.amihaliov.shower_singer_java.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class BootStrapLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BootStrapLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (!userRepository.findByUserName("admin").isPresent()) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Arrays.asList(
                    new Role("ROLE_ADMIN"),
                    new Role("ROLE_USER")
            ));
            userRepository.save(user);
        }

        if (!userRepository.findByUserName("user").isPresent()) {
            User user2 = new User();
            user2.setUserName("user");
            user2.setPassword(passwordEncoder.encode("user"));
            user2.getRoles().add(new Role("ROLE_USER"));
            userRepository.save(user2);
        }


    }
}
