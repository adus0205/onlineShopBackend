package pl.szupke.onlineShop.cart.controller.mapper;

import pl.szupke.onlineShop.cart.controller.dto.CartSummaryDto;
import pl.szupke.onlineShop.cart.controller.dto.CartSummaryItemDto;
import pl.szupke.onlineShop.cart.controller.dto.ProductDto;
import pl.szupke.onlineShop.cart.controller.dto.SummaryDto;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.CartItem;
import pl.szupke.onlineShop.common.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {

    public static CartSummaryDto mapToCartSummary(Cart cart){
        return CartSummaryDto.builder()
                .id(cart.getId())
                .items(mapCartItems(cart.getItems()))
                .summary(mapToSummary(cart.getItems()))
                .build();
    }

    private static List<CartSummaryItemDto> mapCartItems (List<CartItem> items){
        return items.stream()
                .map(CartMapper::mapToCartItem)
                .toList();
    }

    private static CartSummaryItemDto mapToCartItem(CartItem cartItem){
        return CartSummaryItemDto.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(mapToProductDto(cartItem.getProduct()))
                .lineValue(calculateLineValue(cartItem))
                .build();
    }


    private static ProductDto mapToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .currency(product.getCurrency())
                .image(product.getImage())
                .price(product.getPrice())
                .slug(product.getSlug())
                .build();
    }

    private static BigDecimal calculateLineValue(CartItem cartItem){
        return cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
    private static SummaryDto mapToSummary(List<CartItem> items){
        return SummaryDto.builder()
                .grossValue(sumValues(items))
                .build();
    }

    private static BigDecimal sumValues(List<CartItem> items){
        return items.stream()
                .map(CartMapper::calculateLineValue)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
