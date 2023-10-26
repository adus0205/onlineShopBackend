package pl.szupke.onlineShop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.common.repository.CartItemRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public void delete(Long id){
        cartItemRepository.deleteById(id);
    }

    public Long cartItemInCart(Long cartId) {
        return cartItemRepository.countByCartId(cartId);
    }
}
