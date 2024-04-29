package com.websummarizer.Web.Summarizer;

import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WebSummarizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSummarizerApplication.class, args);
    }

    // Bean to initialize the database with admin role and user
    @Bean
    CommandLineRunner run(RoleRepo roleRepository, UserRepo userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if the admin role already exists
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

            // Create admin role and user role
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            // Set roles for admin user
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            // Create admin user
            User admin = new User(1, "ADMIN", "ADMIN", "admin@email.com", passwordEncoder.
                    encode("password"), null, null, roles, Provider.LOCAL, "bart");

            // Save admin user
            userRepository.save(admin);
        };
    }

}
