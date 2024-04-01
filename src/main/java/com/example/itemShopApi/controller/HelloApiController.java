package com.example.itemShopApi.controller;

import com.example.itemShopApi.security.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloApiController {

    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/hello")
    public String hello(@RequestHeader("Authorization") String token){

        log.info("token = {}" , token);
        Long userIdFormToken = jwtTokenizer.getUserIdFromToken(token);
        return "hello" + userIdFormToken;
    }
}
