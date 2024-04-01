package com.example.itemShopApi.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 30 * 1000L; //30분
    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7*24*60*60*1000L; //7일

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret){
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * AccessToken 설정
     */
    public String createAccessToken(Long id , String email ,String name, List<String> roles){
        return createToken(id , email , name , roles , ACCESS_TOKEN_EXPIRE_COUNT , accessSecret);
    }

    /**
     * 
     * RefreshToekn 생성
     * 
     */
    public String createRefreshToken(Long id , String email , String name, List<String> roles){
        return createToken(id, email , name, roles , REFRESH_TOKEN_EXPIRE_COUNT, refreshSecret);
    }

    public String createAccessToken(Long id , String email , List<String> roles){
        return createToken(id , email , roles , ACCESS_TOKEN_EXPIRE_COUNT , accessSecret);
    }
    public String createRefreshToken(Long id , String email ,  List<String> roles){
        return createToken(id, email , roles , REFRESH_TOKEN_EXPIRE_COUNT, refreshSecret);
    }

    public String createToken(Long id , String email , List<String> roles, long expire, byte[] secretKey){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles" , roles);
        claims.put("userId" , id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey))
                .compact();

    }

    public String createToken(Long id , String email , String name,List<String> roles, long expire, byte[] secretKey){
        Claims claims = Jwts.claims().setSubject(email);

        claims.put("roles" , roles);
        claims.put("userId" , id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey))
                .compact();

    }

    /**
     * 토큰에서 유저아이디 얻기
     */

    public Long getUserIdFromToken(String token){
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token , accessSecret);
        return Long.valueOf((Integer)claims.get("userId"));
    }

    public Claims parseAccessToken(String accessToken){
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken){
        return parseToken(refreshToken , refreshSecret);
    }

    public Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param secretKey - byte 형식
     * @return Key 형식 시크릿키
     */
    public static Key getSigningKey(byte[] secretKey){
        return Keys.hmacShaKeyFor(secretKey);
    }

}
