package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.CartItem;
import com.example.itemShopApi.dto.AddCartItemDto;
import com.example.itemShopApi.security.jwt.util.IfLogin;
import com.example.itemShopApi.security.jwt.util.LoginUserDto;
import com.example.itemShopApi.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카트상품 컨트롤러", description = "카트상품 API입니다.")

@RequestMapping("/cartItems")
@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "카트에 담기" , description = "카트에 상품을 담습니다.")
    public CartItem addCartItem(
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto,
            @Parameter(name="addCartItemDto" ,description = "addCartItemDto")
            @RequestBody AddCartItemDto addCartItemDto){
        //같은 cart에 간은 product가 있으면 quantity를 더해줘야함

        System.out.println("loginUserDto ++ " + loginUserDto );
        System.out.println("addCartItemDto ++ " + addCartItemDto );

        //이미 있는 상품이라면 수량을 업
        if(cartItemService.isCartItemExist(loginUserDto.getMemberId() , addCartItemDto.getCartId() , addCartItemDto.getProductId())){
            System.out.println("loginUserDto.getMemberId() ==" + loginUserDto.getMemberId());
            System.out.println("addCartItemDto.getCartId() ==" + addCartItemDto.getCartId());
            System.out.println("addCartItemDto.getProductId() ==" + addCartItemDto.getProductId());
            CartItem cartItem = cartItemService.getCartItem(loginUserDto.getMemberId() , addCartItemDto.getCartId() , addCartItemDto.getProductId());
            cartItem.setQuantity(cartItem.getQuantity() + addCartItemDto.getQuantity());
            return cartItemService.updateCartItem(cartItem);
        }

        return cartItemService.addCartItem(addCartItemDto);
    }

    @Operation(summary = "카트에서 삭제" , description = "카트에서 아이템을 삭제합니다.")
    @DeleteMapping("/{cartItemId}") //정해지지않은값을 넘겨받아서 URI로 사용할떈 @PathVariable
    public ResponseEntity deleteCartItem(
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto ,
            @Parameter(name="cartItemId" ,description = "카트에 담긴 상품 ID")
            @PathVariable Long cartItemId){
        if(cartItemService.isCartItemExist(loginUserDto.getMemberId() , cartItemId) == false){
            return ResponseEntity.badRequest().build();
        }
        cartItemService.deleteCartItem(loginUserDto.getMemberId(),cartItemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카트보기" , description = "카트를 조회합니다.")
    @GetMapping //get으로 데이터 받을떈 @RequestParam
    public List<CartItem> getCartItem(
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto,
            @Parameter(name="cartId" ,description = "카드 ID")
            @RequestParam(required = false) Long cartId){
        if(cartId == null){
            return cartItemService.getCartItems(loginUserDto.getMemberId());
        }
        return cartItemService.getCartItems(loginUserDto.getMemberId(), cartId);
    }
}
