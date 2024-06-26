package pl.szupke.onlineShop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.CartItem;
import pl.szupke.onlineShop.cart.model.dto.CartProductDto;
import pl.szupke.onlineShop.common.repository.CartRepository;
import pl.szupke.onlineShop.common.model.Product;
import pl.szupke.onlineShop.common.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }
    @Transactional
    public Cart addProductToCart(Long id, CartProductDto cartProductDto) {
        Cart cart = getInitializedCart(id);
        cart.addProduct(CartItem.builder()
                    .quantity(cartProductDto.quantity())
                    .product(getProduct(cartProductDto.productId()))
                    .cartId(cart.getId())
                .build());
        return cart;
    }

    private Product getProduct(Long productId){
        return productRepository.findById(productId).orElseThrow();
    }

    private Cart getInitializedCart(Long id) {
        if (id == null || id <= 0){
            return saveNewCart();
        }
        return cartRepository.findById(id).orElseGet(this::saveNewCart);
    }

    private Cart saveNewCart() {
        return cartRepository.save(Cart.builder().created(LocalDateTime.now()).build());
    }

    @Transactional
    public Cart updateCart(Long id, List<CartProductDto> cartProductDtos) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cart.getItems().forEach(cartItem -> {
            cartProductDtos.stream()
                    .filter(cartProductDto -> cartItem.getProduct().getId().equals(cartProductDto.productId()))
                    .findFirst()
                    .ifPresent(cartProductDto -> cartItem.setQuantity(cartProductDto.quantity()));
        });
        return cart;
    }
}
