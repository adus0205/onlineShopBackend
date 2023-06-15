package pl.szupke.onlineShop.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.category.model.Category;
import pl.szupke.onlineShop.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
}
