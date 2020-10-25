package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Category;
import org.sam.melchor.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Category>> createCategory(@Valid @RequestBody Category category) {
        categoryRepository.save(category);
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Category>> updateCategory(@Valid @RequestBody Category category,
                                                         @PathVariable Long id) {
        category.setId(id);
        categoryRepository.save(category);
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }
}
