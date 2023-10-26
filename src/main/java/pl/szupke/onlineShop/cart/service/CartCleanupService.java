package pl.szupke.onlineShop.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.repository.CartItemRepository;
import pl.szupke.onlineShop.common.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartCleanupService {

    private  final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Transactional
    @Scheduled(cron = "${app.cart.cleanup.expression}")
    public void cleanupoldCarts(){
        List<Cart> carts = cartRepository.findByCreatedLessThan(LocalDateTime.now().minusDays(3));
        List<Long> ids = carts.stream()
                        .map(Cart::getId)
                        .toList();
        log.info("Stare koszyki" + carts.size());
 //       carts.forEach(cart -> {
 //           cartItemRepository.deleteByCartId(cart.getId());
 //           cartRepository.deleteCartById(cart.getId());
 //       });
        if (!ids.isEmpty()){
            cartItemRepository.deleteAllByCartIdIn(ids);
            cartRepository.deleteAllByIdIn(ids);
        }
    }
}
