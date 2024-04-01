package com.example.itemShopApi.security.jwt.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginInfoDto {
    private Long memberId;
    private String email;
    private String name;

}
