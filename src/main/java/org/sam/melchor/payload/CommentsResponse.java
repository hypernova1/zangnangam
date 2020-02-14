package org.sam.melchor.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommentsResponse {

    private List<CommentResponse> commentList;

    public void set(List<Comment> commentList) {
        List<CommentResponse> newCommentList = new ArrayList<>();
        commentList.forEach(comment -> {
            LocalDateTime date = comment.getCreated();
            String created = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
            CommentResponse newComment = new CommentResponse();
            newComment.setId(comment.getId());
            newComment.setContent(comment.getContent());
            newComment.setWriter(comment.getWriter());
            newComment.setNonMemberName(comment.getNonMemberName());
            newComment.setNonMemberPwd(comment.getNonMemberPwd());
            newComment.setCreated(created);
            newCommentList.add(newComment);
        });
        this.commentList = newCommentList;
    }
}
