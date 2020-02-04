package org.sam.melchor.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.payload.PostRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post extends DateAudit {

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
    private Account writer;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private Integer likeCnt;

    public void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }

    public static Post setPost(PostRequest postRequest, Account account, Category category) {
        Post post = new Post();
        post.setCategory(category);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setWriter(account);
        return post;
    }

    public static Post setPost(Post post, PostRequest postRequest, Account account, Category category) {
        post.setCategory(category);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setWriter(account);
        return post;
    }

}
