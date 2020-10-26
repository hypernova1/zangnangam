package org.sam.melchor.domain;

import lombok.*;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.PostDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Category category;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    private Account writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    private Integer likeCnt;

    public void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }

    public void update(PostDto.UpdateRequest request, Category category) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.category = category;
    }
}
