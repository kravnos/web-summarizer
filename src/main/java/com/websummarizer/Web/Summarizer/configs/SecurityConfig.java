package com.websummarizer.Web.Summarizer.configs;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import com.websummarizer.Web.Summarizer.utils.RSAKeyProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * Configuration class for security-related beans and configurations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RSAKeyProperties keyProperties;
    @Autowired
    AuthenticationService authenticationService;

    /**
     * Constructor for SecurityConfig.
     *
     * @param keyProperties RSAKeyProperties object containing RSA key properties.
     */
    public SecurityConfig(RSAKeyProperties keyProperties) {
        this.keyProperties = keyProperties;
    }

    /**
     * Provides a PasswordEncoder bean for encoding passwords.
     *
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean for authentication purposes.
     *
     * @param detailsService UserDetailsService implementation for retrieving user details.
     * @return AuthenticationManager instance.
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(detailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    /**
     * Configures security filter chain.
     *
     * @param httpSecurity HttpSecurity object for configuring security.
     * @return SecurityFilterChain instance.
     * @throws Exception if configuration fails.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/oauth/**").permitAll();
                    auth.requestMatchers("/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/user/register")//todo change them to valid URL : get now allowed right now
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

                                //todo choose whatever is necessary
                                System.out.println("cccccc"+authentication.getCredentials());
                                System.out.println("cccccc"+authentication.getPrincipal());
                                System.out.println("cccccc"+authentication.getAuthorities());
                                System.out.println("cccccc"+authentication.getDetails());
                                System.out.println("cccccc"+authentication.getName());

                                System.out.println("authentication.getPrinciple() - " + authentication.getPrincipal());
                                DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                                //todo change the way auth service is used
                                authenticationService.processOAuthPostLoginGoogle(oidcUser.getEmail());
                                response.sendRedirect("/list");
                            }
                        })
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



    /**
     * Provides a JwtDecoder bean for decoding JWT tokens.
     *
     * @return NimbusJwtDecoder instance configured with the RSA public key.
     */
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keyProperties.getPublicKey()).build();
    }

    /**
     * Provides a JwtEncoder bean for encoding JWT tokens.
     *
     * @return NimbusJwtEncoder instance configured with RSA key pair.
     */
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keyProperties.getPublicKey()).privateKey(keyProperties.getPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
}