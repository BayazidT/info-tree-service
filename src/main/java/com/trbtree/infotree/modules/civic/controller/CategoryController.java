package com.trbtree.infotree.modules.civic.controller;


import com.trbtree.infotree.modules.civic.dto.CategoryResponseDto;
import com.trbtree.infotree.modules.civic.sevice.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-tree-service/api/v1/private/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category management endpoints")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get category list")
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getCategories();
    }
}
