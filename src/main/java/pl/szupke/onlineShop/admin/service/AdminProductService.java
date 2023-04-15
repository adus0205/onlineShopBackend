package pl.szupke.onlineShop.admin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.admin.model.AdminProduct;
import pl.szupke.onlineShop.admin.repository.AdminProductRepository;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;

    public Page<AdminProduct> getProduct(Pageable pageable){
        return adminProductRepository.findAll(pageable);
    }

    public AdminProduct getProduct(Long id){
        return adminProductRepository.findById(id).orElseThrow();
    }

    public AdminProduct createProduct(AdminProduct adminProduct) {
        return adminProductRepository.save(adminProduct);
    }

    public AdminProduct updateProduct(AdminProduct adminProduct) {
        return adminProductRepository.save(adminProduct);
    }
}
