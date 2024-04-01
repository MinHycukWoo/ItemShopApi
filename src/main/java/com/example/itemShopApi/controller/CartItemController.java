package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.CartItem;
import com.example.itemShopApi.dto.AddCartItemDto;
import com.example.itemShopApi.security.jwt.util.IfLogin;
import com.example.itemShopApi.security.jwt.util.LoginUserDto;
import com.example.itemShopApi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cartItems")
@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public CartItem addCartItem(@IfLogin LoginUserDto loginUserDto, @RequestBody AddCartItemDto addCartItemDto){
        //같은 cart에 간은 product가 있으면 quantity를 더해줘야함
        if(cartItemService.isCartItemExist(loginUserDto.getMemberId() , addCartItemDto.getCartId() , addCartItemDto.getProductId())){
            CartItem cartItem = cartItemService.getCartItem(loginUserDto.getMemberId() , addCartItemDto.getCartId() , addCartItemDto.getProductId());
            cartItem.setQuantity(cartItem.getQuantity() + addCartItemDto.getQuantity());
            return cartItemService.updateCartItem(cartItem);
        }
        return cartItemService.addCartItem(addCartItemDto);
    }


    @DeleteMapping("/{cartItemId}") //정해지지않은값을 넘겨받아서 URI로 사용할떈 @PathVariable
    public ResponseEntity deleteCartItem(@IfLogin LoginUserDto loginUserDto , @PathVariable Long cartItemId){
        if(cartItemService.isCartItemExist(loginUserDto.getMemberId() , cartItemId) == false){
            return ResponseEntity.badRequest().build();
        }
        cartItemService.deleteCartItem(loginUserDto.getMemberId(),cartItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping //get으로 데이터 받을떈 @RequestParam
    public List<CartItem> getCartItem(@IfLogin LoginUserDto loginUserDto, @RequestParam(required = false) Long cartId){
        if(cartId == null){
            return cartItemService.getCartItems(loginUserDto.getMemberId());
        }
        return cartItemService.getCartItems(loginUserDto.getMemberId(), cartId);
    }
}
