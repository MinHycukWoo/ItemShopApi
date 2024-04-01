package com.example.itemShopApi.security.jwt.token;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private Object principal; //로그인한 사용자 id ,email
    private Object credentials;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                  Object principal,
                                  Object credentials){
        super(authorities);
        //log.info("authorities == {}" ,authorities);
        //log.info("principal == {}" ,principal);
        //log.info("credentials == {}" ,credentials);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);

        log.info("this == {}" , this);
    }

    public JwtAuthenticationToken(String token){
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
