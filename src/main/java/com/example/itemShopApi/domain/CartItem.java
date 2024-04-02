package com.example.itemShopApi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Setter
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    /**
     * 어떤 카트에 담겨있는지 표시하기 위해
     * N:1 관계이다
     */

    /**
     * 장바구니에 있는 아이템들의 정보이다.
     * 원본 아이템이 변경되거나 삭제될 경우 장바구니에는 남아있기를 원하기 떄문에
     * FK를 엮지않는다
     */
    private Long productId;

    private String productTitle;

    private Double productPrice;

    private String productDescription;

    private int quantity;
}
