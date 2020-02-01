package org.sam.melchor.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Post;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostListResponse {

    private String categoryName;

    private List<Post> postList;

    private boolean isNext;

    public void setPostList(List<Post> postList) {

        LocalDateTime today = LocalDateTime.now();
        List<Post> newPostList = new ArrayList<>();
        postList.forEach((item) -> {
            Post post = new Post();
            post.setId(item.getId());
            post.setTitle(item.getTitle());
            post.setWriter(item.getWriter());
            post.setCreated(item.getCreated());
            newPostList.add(post);
        });
        this.postList = newPostList;
    }

}
