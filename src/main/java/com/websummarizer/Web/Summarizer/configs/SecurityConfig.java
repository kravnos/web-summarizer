//package com.websummarizer.Web.Summarizer.configs;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/**").permitAll(); // Allow all requests
//                })
//                .oauth2Login(withDefaults())
//                .logout((logout) -> {
//                    logout.logoutUrl("/logout");
//                    logout.logoutSuccessUrl("/");
//                })
//                .build();
//    }
//}
