package com.example.itemShopApi.repository;


import com.example.itemShopApi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {

}
