package com.example.itemShopApi.security.jwt.provider;

import com.example.itemShopApi.security.jwt.filter.JwtAuthenticationFilter;
import com.example.itemShopApi.security.jwt.token.JwtAuthenticationToken;
import com.example.itemShopApi.security.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //log.info("authentication = {} " , authentication);
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken)authentication;
        //토큰을 검증한다 . 기간이 만료되었는지 , 토큰 문자열이 문제가 있느닞 등 Exception이 발생한다
        Claims claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken());
        //sub에 암호화된 데이터를 집어넣고 복호화하는 코드를 넣어줄 수 있다
        String email = claims.getSubject();
        //log.info("claims = {} " , claims);
        //log.info("email = {} " , email);
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        //authentication
        //authenticationToken = new JwtAuthenticationToken(authorities, email , null);
        //log.info("authenticationToken.getPrincipal(); = {} " , authenticationToken.getPrincipal());
        //log.info("authenticationToken.getAuthorities(); = {} " , authenticationToken.getAuthorities());
        log.info("authenticationToken.getAuthorities(); = {} " , authenticationToken.getAuthorities());
        //return authenticationToken;
        log.info("authenticationToken; = {} " , authenticationToken);
        return new JwtAuthenticationToken(authorities, email , null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims){
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles){
            authorities.add(() -> role);
        }
        log.info("authorities = {} " , authorities);
        return authorities;
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
