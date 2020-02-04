package org.sam.melchor.repository;

import org.sam.melchor.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteById(Long id);

    List<Comment> findAllByPostId(Long postId);
}
