package com.example.itemShopApi.service;

import com.example.itemShopApi.domain.Cart;
import com.example.itemShopApi.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public Cart addCart(Long memberId , String date){
        Optional<Cart> cart = cartRepository.findByMemberIdAndDate(memberId , date);

        /**
         * 카트가 비어있다면 새롭게 카트를만들고
         * 이미 카트가 DB에 있다면 그걸 꺼내서 보여준다.
         */
        if(cart.isEmpty()){
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setDate(date);
            Cart saveCart = cartRepository.save(newCart);
            return saveCart;
        }else{
            return cart.get();
        }
    }
}
