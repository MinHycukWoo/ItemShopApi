package com.example.itemShopApi.config;

import com.example.itemShopApi.security.jwt.filter.JwtAuthenticationFilter;
import com.example.itemShopApi.security.jwt.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig extends AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity> {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception{
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        //빌더를 통해서 생성한다.

        builder.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager), //이 필터는 authenticationManager 를 가지고 만들겟다
                UsernamePasswordAuthenticationFilter.class) // 이 동작앞에 위 필터를 거쳐서 받게하겟다.
                .authenticationProvider(jwtAuthenticationProvider); //여기다 이제 jwtAuthenticationProvider를 사용하도록 설정한다.

        /**
         * AuthenticationFilter > AuthenticationManager > AuthenticationProvicer
         * 의 순서에 맞게 코드로 만든것이다.
         */


    }
}
