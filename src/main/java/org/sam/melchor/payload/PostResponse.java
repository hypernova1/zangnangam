package org.sam.melchor.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter @Setter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Account writer;
    private String created;
    private Category category;
    private List<CommentResponse> comments;

    public void set(Post post) {
        LocalDateTime date = post.getCreated();
        String created = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.setCategory(post.getCategory());
        this.created = created;
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.set(post.getComments());
        this.comments = commentsResponse.getCommentList();
    }

}
