package pl.szupke.onlineShop.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.product.model.Product;
import pl.szupke.onlineShop.product.service.ProductService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getProduct(){
        return productService.getProduct();
    }
}
