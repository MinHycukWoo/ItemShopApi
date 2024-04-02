package com.example.itemShopApi.repository;


import com.example.itemShopApi.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {

    /**
     * existsBy Cart _ memberId And Cart _ id And ProductId
     * existsBy : 해당 메서드는 주어진 조건을 만족하는 엔티티가 존재하는지를 검사합니다.
     * Cart_memberId : Cart 엔티티의 memberId 속성에 대한 조건입니다.
     * Cart_id : Cart 엔티티의 id 속성에 대한 조건입니다.
     * ProductId : productId 속성에 대한 조건입니다.
     *
     * select *
     * from cart a
     * where a.memberId = memberId
     * and a.id = id
     * and a.ProductId = ProductId
     */

    boolean existsByCart_memberIdAndCart_idAndProductId(Long memberId , Long cartId , Long productId );

    /**
     * findBy Cart_memberId And Cart_id And ProductId
     * Cart_memberId : Cart 엔티티의 memberId 속성에 대한 조건입니다.
     * Cart_id: Cart 엔티티의 id 속성에 대한 조건입니다.
     * ProductId: productId 속성에 대한 조건입니다.
     */
    Optional<CartItem> findByCart_memberIdAndCart_idAndProductId(Long memberId , Long cartId , Long productId);

    /**
     * existsBy Cart_memberId And Id
     * existsBy: 해당 메서드는 주어진 조건을 만족하는 엔티티가 존재하는지를 검사합니다.
     *
     *
     *
     */

    boolean existsByCart_memberIdAndId(Long memberId , Long cartItemId);
    void deleteByCart_memberIdAndId(Long memberId , Long cartItemId);

    List<CartItem> findByCart_memberIdAndCart_id(Long memberId , Long cartId);
    List<CartItem> findByCart_memberId(Long memberId);
}
