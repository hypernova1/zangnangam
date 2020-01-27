package org.sam.melchor.repository;

import org.sam.melchor.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

     Optional<Category> findByPath(String category);
}
