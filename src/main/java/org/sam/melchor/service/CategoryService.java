package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sam.melchor.domain.Category;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.web.payload.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categories;
    private final ModelMapper modelMapper;

    public List<CategoryDto> getCategoryList() {
        List<Category> categoryList = categories.findAll();
        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public void register(@Valid CategoryDto request) {
        Category category = Category.builder()
                .name(request.getName())
                .path(request.getPath())
                .orderNo(request.getOrderNo())
                .role(request.getRole())
                .build();
        categories.save(category);
    }

    @Transactional
    public void update(Long id, CategoryDto request) {
        Category category = categories.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        category.update(request);
    }

    public void delete(Long id) {
        categories.deleteById(id);
    }
}
