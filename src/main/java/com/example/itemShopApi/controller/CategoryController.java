package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Category;
import com.example.itemShopApi.dto.AddCategoryDto;
import com.example.itemShopApi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 컨트롤러", description = "카테고리 API입니다.")

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "카테고리 추가 (관리자 권한 필요)" , description = "카테고리를 추가합니다.")
    public Category addCategory(
            @Parameter(name="addCategoryDto" ,description = "카테고리 ID")
            @RequestBody AddCategoryDto addCategoryDto){
        System.out.println("addCategoryDto ++" + addCategoryDto);
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping
    @Operation(summary = "카테고리 조회" , description = "카테고리를 조회합니다.")
    public List<Category> getAllCategories(){
        return categoryService.getCategories();
    }
}
