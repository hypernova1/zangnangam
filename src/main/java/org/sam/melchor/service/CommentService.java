package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.exception.CommentNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CommentRepository;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.config.security.UserPrincipal;
import org.sam.melchor.web.payload.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository comments;
    private final AccountRepository accounts;
    private final PostRepository posts;
    private final ModelMapper modelMapper;


    @Transactional
    public void registerComment(CommentDto.Request request) {
        Account account = null;
        if (StringUtils.hasText(request.getEmail())) {
            account = accounts.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AccountNotFoundException(request.getEmail()));
        }

        Post post = posts.findById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException(request.getPostId()));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .writer(account)
                .nonMemberName(request.getNonMemberName())
                .nonMemberPwd(request.getNonMemberPwd())
                .post(post)
                .build();

        comments.save(comment);
    }

    public List<CommentDto.Response> getCommentList(Long postId) {
        List<Comment> commentList = comments.findAllByPostId(postId);
        return commentList.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.Response.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long id, UserPrincipal authUser) {
        Account writer = accounts.findById(authUser.getId())
                .orElseThrow(() -> new AccountNotFoundException(authUser.getEmail()));

        comments.deleteByIdAndWriter(id, writer);
    }

    @Transactional
    public void updateComment(Long id, CommentDto.@Valid Request request) {
        Comment comment = comments.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        comment.update(request);
    }
}
