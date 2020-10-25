package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.CommentRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
public class Comment extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NonNull
    private String content;

    @ManyToOne
    private Account writer;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Post post;

    private String nonMemberName;
    private String nonMemberPwd;

    public static Comment setComment(CommentRequest commentRequest, Post post, Account writer) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setWriter(writer);
        comment.setPost(post);
        comment.setNonMemberName(commentRequest.getNonMemberName());
        comment.setNonMemberPwd(commentRequest.getNonMemberPwd());
        return comment;
    }

}
