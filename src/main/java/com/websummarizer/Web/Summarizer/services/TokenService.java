package com.websummarizer.Web.Summarizer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Service class for handling JWT token-related operations.
 */
@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;

    /**
     * Generates a JWT token based on the provided authentication.
     *
     * @param authentication The authentication object.
     * @return The generated JWT token.
     */
    public String generateJwt(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .claim("role",scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}