package com.example.bidhub.jwt;

import com.example.bidhub.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtParser;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 토큰을 생성해주고 검증해주는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private SecretKey key;
    private JwtParser parser;

    public String createToken(Member member, Duration expiredAt) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiredAt.toMillis());

        return Jwts.builder()
                .header().add(getHeader()).and()
                .claims()
                    .issuedAt(now)
                    .issuer(jwtProperties.getIssuer())
                    .subject(member.getMemId())
                    .expiration(exp)
                    .add("role", "user").and()
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }
    private SecretKey getKey() {
        if(key==null) {
            key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecretKey()));
        }
        return key;
    }
    private Map<String, Object> getHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }



    public boolean isValidToken(String token){
        if(parser == null) {
            parser = Jwts.parser().verifyWith(getKey()).build();
        }
        try {
            parser.parseSignedClaims(token);
            return true;
        }catch(Exception e) {
            return false;
        }
    }


    public Claims getClaims(String token) {
        if(parser == null) {
            parser = Jwts.parser().verifyWith(getKey()).build();
        }
        try {
            Jws<Claims> jws = parser.parseSignedClaims(token);
            return jws.getPayload();
        }catch(Exception e) {
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = claims.get("role", String.class);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));
        UserDetails userDetails = User
                .withUsername(claims.getSubject())
                .password(claims.getSubject())
                .roles(role)
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }
}
