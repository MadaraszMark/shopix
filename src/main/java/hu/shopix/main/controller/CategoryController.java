package hu.shopix.main.controller;

import hu.shopix.main.model.Category;
import hu.shopix.main.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    // 1. nagyon egyszerű lista – csak ellenőrzéshez
    @GetMapping
    public Page<Category> list(@ParameterObject Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    // 2. (opcionális) lekérés id alapján – ha szeretnéd gyorsan kipróbálni
    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}

