package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Cart;
import com.example.itemShopApi.dto.AddCartDto;
import com.example.itemShopApi.security.jwt.util.IfLogin;
import com.example.itemShopApi.security.jwt.util.LoginUserDto;
import com.example.itemShopApi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartApiController {
    private final CartService cartService;

    /**
     * 카트를 생성할 User의 ID를 넣어준다.
     * @param
     * @return
     */
    @PostMapping
    public Cart addCart(@IfLogin LoginUserDto loginUserDto , @RequestBody AddCartDto addCartDto){
        System.out.println("loginUserDto ++" + loginUserDto);
        LocalDate localDate = LocalDate.now();
        localDate.getYear();
        localDate.getDayOfMonth();
        localDate.getMonthValue();
        String date = String.valueOf(localDate.getYear())
                + (localDate.getMonthValue() < 10 ? "0" : "")
                + String.valueOf(localDate.getMonthValue())
                + (localDate.getDayOfMonth() < 10 ? "0" : "")
                + String.valueOf(localDate.getDayOfMonth());
        Cart cart = cartService.addCart(addCartDto.getMemberId(),date);
        return cart;
    }
}
