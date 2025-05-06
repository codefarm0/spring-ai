package com.codefarm.openai.demo.service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiServiceTest {

    @Autowired
    private OpenAiService openAiService;
    @Test
    void getAnswer() {

        String ans = openAiService.getAnswer("What is the capital of INDIA?");

        System.out.println("Response: " + ans);
    }
}