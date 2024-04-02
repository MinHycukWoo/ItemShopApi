package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCategoryDto {
    @Schema(description = "카테고리 이름" , nullable = false , example = "상의")
    private String name;

}
