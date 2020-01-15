package org.sam.melchor.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sam.melchor.domain.Post;
import org.sam.melchor.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostControllerTest {

    @BeforeAll
    public void addData() {
        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setContent("post" + i);
            post.setTitle("title" + i);
            postRepository.save(post);

        }
    }

    @Autowired
    private PostRepository postRepository;

    @Test
    public void getPostList() {

    }

}