package com.ngohoainam.music_api.configuration;

import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findUserByEmail("admin@gmail.com").isEmpty()){
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRoles(Roles.ADMIN);
            admin.setDisplayName("Admin");
            admin.setStatus("ACTIVE");
            admin.setEmailVerified(true);
            admin.setCreatedAt(Instant.now());
            admin.setUpdatedAt(Instant.now());
            userRepository.save(admin);
            System.out.println("Admin created");
        }

    }
}

