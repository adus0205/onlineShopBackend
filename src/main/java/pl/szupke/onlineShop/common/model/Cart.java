package pl.szupke.onlineShop.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cartId")
    private List<CartItem> items;

    public void addProduct(CartItem cartItem){
        if (items == null){
            items = new ArrayList<>();
        }
        items.stream()
                        .filter(cartItem1 -> Objects.equals(cartItem.getProduct().getId(),cartItem1.getProduct().getId()))
                                .findFirst()
                                        .ifPresentOrElse(
                                                cartItem1 -> cartItem1.setQuantity(cartItem1.getQuantity() + 1),
                                                ()->items.add(cartItem)
                                        );

    }

}
