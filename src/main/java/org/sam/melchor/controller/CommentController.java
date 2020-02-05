package org.sam.melchor.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.payload.CommentRequest;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.CommentRepository;
import org.sam.melchor.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<List<Comment>> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        Account account = null;
        if (StringUtils.hasText(commentRequest.getEmail())) {
            account = accountRepository.findByEmail(commentRequest.getEmail())
                    .orElseThrow(() -> new AccountNotFoundException(commentRequest.getEmail()));
        }

        Category category = categoryRepository.findByPath(commentRequest.getCategoryPath())
                .orElseThrow(() -> new CategoryNotFoundException(commentRequest.getCategoryPath()));

        Post post = postRepository.findByCategoryAndId(category, commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId()));

        Comment comment = Comment.setComment(commentRequest, post, account);

        commentRepository.save(comment);

        List<Comment> Comments = commentRepository.findAllByPostId(commentRequest.getPostId());

        return ResponseEntity.ok(Comments);
    }

    @PutMapping("{id}")
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment,
                                                 @PathVariable Long id) {
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
