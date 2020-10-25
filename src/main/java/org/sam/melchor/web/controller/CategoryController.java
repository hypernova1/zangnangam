package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.service.CategoryService;
import org.sam.melchor.web.payload.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategory() {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto request) {
        categoryService.register(request);
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto request,
                                                         @PathVariable Long id) {
        categoryService.update(id, request);
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }
}
