package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Cart;
import com.example.itemShopApi.dto.AddCartDto;
import com.example.itemShopApi.security.jwt.util.IfLogin;
import com.example.itemShopApi.security.jwt.util.LoginUserDto;
import com.example.itemShopApi.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "카트 컨트롤러", description = "카트 API입니다.")

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
    @Operation(summary = "카트 생성" , description = "카트를 등록합니다.")
    @PostMapping
    public Cart addCart(
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto ,
            @Parameter(name="addCartDto" ,description = "addCartDto")
            @RequestBody AddCartDto addCartDto){
        System.out.println("loginUserDto ++" + loginUserDto);
        System.out.println("addCartDto ++" + addCartDto);
        LocalDate localDate = LocalDate.now();
        localDate.getYear();
        localDate.getDayOfMonth();
        localDate.getMonthValue();
        String date = String.valueOf(localDate.getYear())
                + (localDate.getMonthValue() < 10 ? "0" : "")
                + String.valueOf(localDate.getMonthValue())
                + (localDate.getDayOfMonth() < 10 ? "0" : "")
                + String.valueOf(localDate.getDayOfMonth());
        //Cart cart = cartService.addCart(addCartDto.getMemberId(),date);
        Cart cart = cartService.addCart(addCartDto.getMemberId(),date);
        return cart;
    }
}
