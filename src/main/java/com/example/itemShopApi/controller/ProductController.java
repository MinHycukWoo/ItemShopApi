package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Product;
import com.example.itemShopApi.dto.AddProductDto;
import com.example.itemShopApi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product addProduct(@RequestBody AddProductDto addProductDto){
        return productService.addProduct(addProductDto);
    }

    @GetMapping
    //@RequestParam(required = false, defaultValue = "0") 값이 없을수도 있으며 없다면 기본값0으로 대체
    public Page<Product> getProduct(@RequestParam(required = false, defaultValue = "0") Long categoryId,
                                    @RequestParam(required = false, defaultValue = "0") int page){
        int size = 10;
        if(categoryId == 0){
            return productService.getProducts(page,size);
        }else{
            return productService.getProducts(categoryId , page , size);
        }
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable Long id){
        return productService.getProduct(id);
    }
}
