package com.example.itemShopApi.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable//임베디드 요소로 선언
@Setter
@Getter
public class Rating {

    private Double rate;
    private Integer count;
}
