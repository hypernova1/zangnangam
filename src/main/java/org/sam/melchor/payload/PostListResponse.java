package org.sam.melchor.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostListResponse {

    String categoryName;

    List<Post> postList;

    public void setPostList(List<Post> postList) {
        List<Post> newPostList = new ArrayList<>();
        postList.forEach((item) -> {
            Post post = new Post();
            post.setId(item.getId());
            post.setTitle(item.getTitle());
            post.setWriter(item.getWriter());
            newPostList.add(post);
        });
        this.postList = newPostList;
    }

}
