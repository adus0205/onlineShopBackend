package pl.szupke.onlineShop.admin.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.admin.category.model.AdminCategory;
import pl.szupke.onlineShop.admin.category.repository.AdminCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final AdminCategoryRepository adminCategoryRepository;

    public List<AdminCategory> getCategories() {
        return adminCategoryRepository.findAll();
    }

    public AdminCategory getCategory(Long id) {
        return adminCategoryRepository.findById(id).orElseThrow();
    }

    public AdminCategory createCategory(AdminCategory adminCategory) {
        return adminCategoryRepository.save(adminCategory);
    }

    public AdminCategory updateCategory(AdminCategory adminCategory) {
        return adminCategoryRepository.save(adminCategory);
    }

    public void deleteCategory(Long id) {
        adminCategoryRepository.deleteById(id);
    }
}
