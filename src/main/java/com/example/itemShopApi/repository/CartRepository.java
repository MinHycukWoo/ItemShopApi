package com.example.itemShopApi.repository;

import com.example.itemShopApi.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberIdAndDate(Long memberId , String date);
}
