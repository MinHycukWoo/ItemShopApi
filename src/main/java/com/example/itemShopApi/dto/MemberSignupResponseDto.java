package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberSignupResponseDto {
    private Long memberId;
    private String email;
    private String name;
    private LocalDateTime regdate;
}
