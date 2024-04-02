package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
