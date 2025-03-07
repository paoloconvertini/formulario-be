package it.calolenoci.service;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import it.calolenoci.dto.LoginDTO;
import it.calolenoci.entity.User;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class TokenService {

    public LoginDTO generateToken(User user) {
        LoginDTO dto = new LoginDTO();
        Set<String> roles = user.roles.stream()
                .map(role -> role.name)
                .collect(Collectors.toSet());
        long l = (System.currentTimeMillis() + 86400000) / 1000;
        JwtClaimsBuilder builder = Jwt.upn(user.username);
        dto.setIdToken(builder.claim(Claims.full_name, user.getFullName())
                .subject("fo-api-service").expiresAt(l).groups(roles).sign());
        dto.setExpireIn(l);
        dto.setError(Boolean.FALSE);
        return dto;
    }
}
