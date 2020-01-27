package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Category;
import org.sam.melchor.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

}
