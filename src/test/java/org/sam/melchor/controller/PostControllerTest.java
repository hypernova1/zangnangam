package org.sam.melchor.controller;

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

import static org.junit.jupiter.api.Assertions.*;

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
            postRepository.save(post);
        }
    }


    @Test
    public void getPostList() {

    }

}