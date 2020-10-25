package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostsResponse {

    private String categoryName;

    private List<PostResponse> postList;

    private boolean isNext;

    public void set(List<Post> postList) {
        List<PostResponse> newPostList = new ArrayList<>();
        postList.forEach((post) -> {
            PostResponse newPost = new PostResponse();
            LocalDateTime date = post.getCreated();
            String created = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            newPost.setId(post.getId());
            newPost.setTitle(post.getTitle());
            newPost.setWriter(post.getWriter());
            newPost.setCreated(created);
            newPostList.add(newPost);
        });
        this.postList = newPostList;
    }

}
