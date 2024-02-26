package com.websummarizer.Web.Summarizer.configs;

import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//
//    // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#oauth2Login(org.springframework.security.config.Customizer)
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/login").authenticated();
//                    auth.anyRequest().permitAll();
//                })
//                .oauth2Login(withDefaults())
//                .logout((logout) -> {
//                    logout.logoutUrl("/logout");
//                    logout.logoutSuccessUrl("/");
//                })
//
//                .build();
//    }
}
