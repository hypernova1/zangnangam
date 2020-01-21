package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.repository.CommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
