package org.sam.melchor.domain;

import lombok.*;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.CommentDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Comment extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String content;

    @ManyToOne
    private Account writer;

    @ManyToOne
    private Post post;

    private String nonMemberName;
    private String nonMemberPwd;

    @Builder
    public Comment(String content, Account writer, Post post, String nonMemberName, String nonMemberPwd) {
        this.content = content;
        this.writer = writer;
        this.post = post;
        this.nonMemberName = nonMemberName;
        this.nonMemberPwd = nonMemberPwd;
    }

    @Builder
    public Comment(Account writer, Post post) {
        this.writer = writer;
        this.post = post;
    }

    public void update(CommentDto.Request request) {
        this.content = request.getContent();
    }

}
