package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.config.security.AuthUser;
import org.sam.melchor.domain.Account;
import org.sam.melchor.service.CommentService;
import org.sam.melchor.web.payload.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto.Request request,
                                                               @AuthUser Account account) {

        if(account == null &&
                (request.getNonMemberName() == null || request.getNonMemberPwd() == null)) {
            return ResponseEntity.badRequest().build();
        }

        commentService.registerComment(request);
        List<CommentDto.Response> commentList = commentService.getCommentList(request.getPostId());

        return ResponseEntity.ok(commentList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentDto.Request request,
                                           @AuthUser Account account) {

        commentService.deleteComment(id, account);
        List<CommentDto.Response> commentList = commentService.getCommentList(request.getPostId());
        return ResponseEntity.ok(commentList);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentDto.Request request) {

        commentService.updateComment(id, request);
        List<CommentDto.Response> commentList = commentService.getCommentList(request.getPostId());
        return ResponseEntity.ok(commentList);
    }

}
