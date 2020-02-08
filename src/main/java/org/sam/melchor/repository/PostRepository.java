package org.sam.melchor.repository;

import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findByCategoryId(Long category_id, Pageable pageable);

    Optional<Post> findByCategoryAndId(Category category, Long id);

    Optional<Post> findByIdAndWriter(Long id, Account writer);

    Long deleteByIdAndWriter(Long id, Account writer);
}
