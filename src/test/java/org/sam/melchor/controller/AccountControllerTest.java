package org.sam.melchor.controller;

import org.junit.jupiter.api.Test;
import org.sam.melchor.domain.Account;
import org.sam.melchor.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void checkEmail() throws Exception {
        mockMvc.perform(get("/auth/check/melchor"))
                .andExpect(status().isOk());
    }

    @Test
    public void signup() throws Exception {
        mockMvc.perform(post("/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\": \"melchor@gmail.com\", \"name\": \"sam\", \"password\": \"1111\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void signin() throws Exception {
        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"melchor@gmail.com\", \"password\": \"1111\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void modify() throws Exception {
        Account account = new Account();
        account.setEmail("chtlstjd01@gmail.com");
        account.setName("melchor");
        account.setPassword("1111");
        Account save = accountRepository.save(account);
        mockMvc.perform(put("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": " + save.getId() + ", \"email\": \"chtlstjd01@gmail.com\", \"password\": \"2222\", \"name\": \"sam\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}