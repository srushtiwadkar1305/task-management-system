package com.taskmanager.security;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.taskmanager.entity.User;
import com.taskmanager.enums.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

@Component
public class JwtUtil {

    private final Key secretKey;
    private final long jwtExpirationMs = 1000*60*60*24;

    public JwtUtil() throws WeakKeyException {

        String secret = "MySuperSecretKeyShouldBeVeryPowerful";
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String generateToken(User user){

        Set<Permission> permissions = RolePermissionConfig.getRoleBasedPermission().get(user.getRole());

        return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("role", user.getRole().name())
        .claim("permissions",
                permissions.stream()
                        .map(Enum::name)
                        .collect(Collectors.toList()))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();

    }

    public boolean validateToken(String token){
        try {

            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;

        } catch (Exception e) {

            System.out.println("JWT TOKEN EXPIRED...");
            return false;

        }
    }

    public Claims getClaims(String token){

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

    }

    public String getUserEmail(String token){
        return getClaims(token).getSubject();
    }

    public String extractToken(String header){

        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }

        return null;
        
    }

}
