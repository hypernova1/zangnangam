package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.exception.CommentNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.web.payload.CommentsResponse;
import org.sam.melchor.web.payload.CommentRequest;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CommentRepository;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.security.AuthUser;
import org.sam.melchor.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest commentRequest,
                                                               @AuthUser UserPrincipal authUser) {

        if(authUser == null &&
                (commentRequest.getNonMemberName() == null || commentRequest.getNonMemberPwd() == null)) {
            return ResponseEntity.badRequest().build();
        }
        Account account = null;
        if (StringUtils.hasText(commentRequest.getEmail())) {
            account = accountRepository.findByEmail(commentRequest.getEmail())
                    .orElseThrow(() -> new AccountNotFoundException(commentRequest.getEmail()));
        }

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId()));

        Comment comment = Comment.setComment(commentRequest, post, account);
        commentRepository.save(comment);

        CommentsResponse commentsResponse = getCommentsResponse(commentRequest);

        return ResponseEntity.ok(commentsResponse.getCommentList());
    }



    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentRequest commentRequest,
                                           @AuthUser UserPrincipal userPrincipal) {

        Account writer = accountRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new AccountNotFoundException(userPrincipal.getEmail()));

        commentRepository.deleteByIdAndWriter(id, writer);
        CommentsResponse commentsResponse = getCommentsResponse(commentRequest);
        return ResponseEntity.ok(commentsResponse.getCommentList());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentRequest commentRequest) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        comment.setContent(commentRequest.getContent());

        commentRepository.save(comment);

        CommentsResponse commentsResponse = getCommentsResponse(commentRequest);

        return ResponseEntity.ok(commentsResponse.getCommentList());
    }

    private CommentsResponse getCommentsResponse(@RequestBody @Valid CommentRequest commentRequest) {
        List<Comment> comments = commentRepository.findAllByPostId(commentRequest.getPostId());
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.set(comments);
        return commentsResponse;
    }

}
