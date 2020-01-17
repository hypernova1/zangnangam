package org.sam.melchor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

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

}