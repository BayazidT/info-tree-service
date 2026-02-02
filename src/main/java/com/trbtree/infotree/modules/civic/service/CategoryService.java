package com.trbtree.infotree.modules.civic.service;

import com.trbtree.infotree.modules.civic.dto.CategoryResponseDto;
import com.trbtree.infotree.modules.civic.entity.Category;
import com.trbtree.infotree.modules.civic.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getParent() != null ? category.getParent().getId() : null,
                        category.getDomain()
                ))
                .toList();
    }

}
