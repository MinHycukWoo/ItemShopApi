package com.example.itemShopApi.security.jwt.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginUserDto {
    @Schema(description = "사용자 이메일" , nullable = false , example = "test@gmail.com")
    private String email;
    @Schema(description = "사용자 이름" , nullable = false , example = "홍길동")
    private String name;
    @Schema(description = "사용자 ID" , nullable = false , example = "1")
    private Long memberId;

    @Schema(description = "사용자 권한" , nullable = false , example = "ROLE_USER")
    private List<String> roles = new ArrayList<>();

    public void addRole(String role){
        roles.add(role);

    }}
