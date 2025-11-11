package com.examenSpringBoot_Koc.Service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    public String extractEmail(String token);
}
