package pl.szupke.onlineShop.cart.model;

import jakarta.persistence.*;
import lombok.*;
import pl.szupke.onlineShop.common.model.Product;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @OneToOne
    private Product product;
    private Long cartId;
}
