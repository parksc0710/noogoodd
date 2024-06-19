package com.noogoodd.api.util;

import com.noogoodd.api.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey aesKey;
    private final Long expiration = 3600000L;

    public JwtUtil() throws Exception {
        this.aesKey = AESUtil.generateKey();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Long userId, User user) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", AESUtil.encrypt(String.valueOf(userId), aesKey));
        claims.put("userEmail", AESUtil.encrypt(user.getEmail(), aesKey));
        claims.put("userType", AESUtil.encrypt(user.getSign_type(), aesKey));
        claims.put("roles", AESUtil.encrypt(user.getAuthorities().toString(), aesKey));
        return createToken(claims, String.valueOf(userId));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();
    }

    public Boolean validateToken(String token, User user) throws Exception {
        final Long userId = extractUserId(token);
        return (userId.equals(user.getId()) && !isTokenExpired(token));
    }

    public Long extractUserId(String token) throws Exception {
        String encryptedUserId = extractClaim(token, claims -> claims.get("userId", String.class));
        return Long.valueOf(AESUtil.decrypt(encryptedUserId, aesKey));
    }

    public String extractUserEmail(String token) throws Exception {
        String encryptedUserEmail = extractClaim(token, claims -> claims.get("userEmail", String.class));
        return AESUtil.decrypt(encryptedUserEmail, aesKey);
    }

    public String extractUserType(String token) throws Exception {
        String encryptedUserType = extractClaim(token, claims -> claims.get("userType", String.class));
        return AESUtil.decrypt(encryptedUserType, aesKey);
    }

    public Long checkJWTToken(HttpServletRequest request) throws Exception{
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception();
        }

        String jwt = authorizationHeader.substring(7);
        return this.extractUserId(jwt);
    }
}
