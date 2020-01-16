package org.sam.melchor.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Post;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;
    @BeforeAll
    public void addData() {

        Account account = new Account();
        account.setEmail("chtlstjd01@gmail.com");
        account.setName("melchor");
        account.setPassword("1111");
        accountRepository.save(account);

        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setContent("post" + i);
            post.setTitle("title" + i);
            post.setWriter(account);
            post.setLikeCnt(i);
            postRepository.save(post);
        }
    }


    @Test
    public void getPostList() throws Exception {
        mockMvc.perform(get("/post/list"))
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Test
    public void getPostListWithTitle() throws Exception {
        mockMvc.perform(get("/post/list")
                        .param("title", "title0"))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void getPostGraterThanLike() throws Exception {

        mockMvc.perform(get("/post/list")
                        .param("likesGreaterThan", "8"))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

}