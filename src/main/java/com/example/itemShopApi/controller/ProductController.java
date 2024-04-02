package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Product;
import com.example.itemShopApi.dto.AddProductDto;
import com.example.itemShopApi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 컨트롤러", description = "상품 API입니다.")

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "상품등록 (관리자 권한 필요)" , description = "상품을 등록합니다.")
    public Product addProduct(
            @Parameter(name="addProductDto" ,description = "등록상품 정보")
            @RequestBody AddProductDto addProductDto){
        return productService.addProduct(addProductDto);
    }

    @GetMapping
    @Operation(summary = "상품조회" , description = "상품을 조회합니다.")
    //@RequestParam(required = false, defaultValue = "0") 값이 없을수도 있으며 없다면 기본값0으로 대체
    public Page<Product> getProduct(
            @Parameter(name="categoryId" ,description = "카테고리 ID")
            @RequestParam(required = false, defaultValue = "0") Long categoryId,
            @Parameter(name="page" ,description = "페이지 정보")
            @RequestParam(required = false, defaultValue = "0") int page){
        int size = 10;
        if(categoryId == 0){
            return productService.getProducts(page,size);
        }else{
            return productService.getProducts(categoryId , page , size);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 단건조회" , description = "상품을 단건으로 조회합니다.")
    public Product getProducts(
            @Parameter(name="id" ,description = "상품 ID")
            @PathVariable Long id){
        return productService.getProduct(id);
    }
}
