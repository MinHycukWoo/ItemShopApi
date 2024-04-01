package com.example.itemShopApi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.management.openmbean.ArrayType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Setter
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String date; //yyyymmdd

    @JsonManagedReference //양방향 참조에서 사용
    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
    /**
     *  Cart엔티티에 담긴 CartItem을 의미한다,
     *  1:N 이라 하나의 cart는 여러개의 아이템을 가질수있다.
     */
    


}
