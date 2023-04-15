package pl.szupke.onlineShop.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.product.model.Product;
import pl.szupke.onlineShop.product.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @GetMapping("/products")
    public Page<Product> getProduct(Pageable pageable){
        return productService.getProduct(pageable);
    }
}
