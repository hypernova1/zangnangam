package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Category category;

    @NotBlank
    private String title;

    @NotBlank
    @Lob
    private String content;

    @NonNull
    @ManyToOne
    @JsonBackReference
    private Account writer;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    private Integer likeCnt;

    public void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }

}
