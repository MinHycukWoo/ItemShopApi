package com.example.itemShopApi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double price;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    /**
     * 이 상품이 어떤 카테고리에 속해있는지를 말한다.
     */


    private String imageUrl;

    /**
     * 임베디드 테이블 사용
     * 임베디드는 여러가지 필드가 포함된걸 하나의 객체로서 표현한것
     */
    @Embedded 
    private Rating rating;
}
