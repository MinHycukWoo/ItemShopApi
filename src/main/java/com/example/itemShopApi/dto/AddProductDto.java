package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddProductDto {
    @Schema(description = "상품 제목" , nullable = false , example = "티셔츠")
    private String title;

    @Schema(description = "상품 가격" , nullable = false , example = "10000")
    private Double price;

    @Schema(description = "상품 설명" , nullable = false , example = "티셔스 입니다.")
    private String description;

    @Schema(description = "카테고리 ID" , nullable = false , example = "1")
    private Long categoryId;

    @Schema(description = "이미지 경로" , nullable = false , example = "/image/")
    private String imageUrl;
}
