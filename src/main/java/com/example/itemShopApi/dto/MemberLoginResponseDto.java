package com.example.itemShopApi.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponseDto {

    private String accessToken;
    private String refreshToken;

    private Long memberId;
    private String nickname;
}
