package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCartItemDto {


    @Schema(description = "카드 ID" , nullable = false , example = "1")
    private Long cartId;

    @Schema(description = "상품 ID" , nullable = false , example = "1")
    private Long productId;

    @Schema(description = "상품 제목" , nullable = false , example = "티셔츠")
    private String productTitle;

    @Schema(description = "상품 가격" , nullable = false , example = "10000")
    private Double productPrice;

    @Schema(description = "상품 설명" , nullable = true , example = "티셔츠 입니다.")
    private String productDescription;

    @Schema(description = "상품 수량" , nullable = false , example = "1")
    private int quantity;
}
