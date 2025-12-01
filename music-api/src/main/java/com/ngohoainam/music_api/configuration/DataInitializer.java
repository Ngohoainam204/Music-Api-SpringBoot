package com.ngohoainam.music_api.configuration;

import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.repository.UserRepository;
import com.ngohoainam.music_api.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
            userRepository.save(admin);
            System.out.println("Admin created");
        }

    }
}
