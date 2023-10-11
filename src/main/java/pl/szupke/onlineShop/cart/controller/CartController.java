package pl.szupke.onlineShop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.szupke.onlineShop.cart.controller.dto.CartSummaryDto;
import pl.szupke.onlineShop.cart.controller.mapper.CartMapper;
import pl.szupke.onlineShop.cart.model.dto.CartProductDto;
import pl.szupke.onlineShop.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @GetMapping("/{id}")
    public CartSummaryDto getCart(@PathVariable Long id){
        return CartMapper.mapToCartSummary(cartService.getCart(id));
    }
    @PutMapping("/{id}")
    public CartSummaryDto addProductToCart(@PathVariable Long id,@RequestBody CartProductDto cartProductDto){
        return CartMapper.mapToCartSummary(cartService.addProductToCart(id, cartProductDto));
    }
    @PutMapping("/{id}/update")
    public CartSummaryDto updateCart(@PathVariable Long id, @RequestBody List<CartProductDto> cartProductDtos){
        return CartMapper.mapToCartSummary(cartService.updateCart(id, cartProductDtos));
    }
}
