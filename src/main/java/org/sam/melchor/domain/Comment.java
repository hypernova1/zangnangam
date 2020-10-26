package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.CommentDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void update(CommentDto.Request request) {
        this.content = request.getContent();
    }

}
