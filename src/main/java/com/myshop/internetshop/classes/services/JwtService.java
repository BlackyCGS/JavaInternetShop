package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.entities.RefreshToken;
import com.myshop.internetshop.classes.exceptions.InvalidTokenException;
import com.myshop.internetshop.classes.repositories.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh.expiration-time}")
    private long refreshExpirationTime;

    RefreshTokenRepository refreshTokenRepository;
    CustomUserDetailsService userDetailsService;
    UserService userService;

    @Autowired
    public JwtService(RefreshTokenRepository refreshTokenRepository,
                      CustomUserDetailsService userDetailsService,
                      UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> generateTokenPair(UserDetails userDetails) {
        return generateTokenPair(new HashMap<>(), userDetails);
    }

    public List<String> generateTokenPair(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return buildTokenPair(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    )
    {
        extraClaims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String buildRefreshToken(UserDetails userDetails) {
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpirationTime(this.extractExpiration(token));
        refreshToken.setId(this.extractId(token));
        refreshToken.setExpired(false);
        refreshToken.setUserId(userService.getIdByUsername(this.extractUsername(token)));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    List<String> buildTokenPair(Map<String, Object> extraClaims,
                                UserDetails userDetails,
                                long expiration) {
        List<String> tokens = new ArrayList<>();
        tokens.add(buildToken(extraClaims, userDetails, expiration));
        tokens.add(buildRefreshToken(userDetails));
        return tokens;
    }

    public boolean validateRefreshToken(String token) {
        if (refreshTokenRepository.existsByToken(token)) {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
           return !refreshToken.isExpired();
        } else {
            throw new InvalidTokenException("Invalid Token");
        }
    }
    public boolean isRefreshTokenValid(String refreshToken) {
        String username = this.extractUsername(refreshToken);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return isTokenValid(refreshToken, userDetails) && this.validateRefreshToken(refreshToken);
    }

    public List<String> updateTokenPair(String refreshToken) {
        String username = this.extractUsername(refreshToken);
        if (this.isRefreshTokenValid(refreshToken)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            return generateTokenPair(userDetails);
        } else {
            throw new InvalidTokenException("Invalid Token");
        }

    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String extractId(String token) {
        return extractClaim(token, Claims::getId);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void revokeUser(int id) {
        refreshTokenRepository.deleteByUserId(id);
    }
}
