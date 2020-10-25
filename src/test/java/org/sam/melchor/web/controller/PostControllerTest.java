package org.sam.melchor.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.domain.Post;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CommentRepository;
import org.sam.melchor.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private CommentRepository commentRepository;

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

        Comment comment = new Comment();
        comment.setWriter(account);
        comment.setPost(postRepository.findById(11L).orElse(new Post()));
        commentRepository.save(comment);

    }

    @Test
    public void getPostList() throws Exception {
        mockMvc.perform(get("/post/list"))
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andDo(print());
    }

    @Test
    public void getPostListWithTitle() throws Exception {
        mockMvc.perform(get("/post/list")
                        .param("title", "title0"))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void getPostGraterThanLike() throws Exception {

        mockMvc.perform(get("/java")
                        .param("page", "1")
                        .param("likesGreaterThan", "8"))
                .andExpect(jsonPath("$.postList", hasSize(10)))
                .andDo(print());
    }

    @Test
    public void getPostView() throws Exception {

        mockMvc.perform(get("/java/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void removePost() throws Exception {

        mockMvc.perform(delete("/post/5")
                .param("categoryPath", "java"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}