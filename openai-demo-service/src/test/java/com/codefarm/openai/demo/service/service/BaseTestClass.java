package com.learn.openai.demo.service.promptin;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
     * Base class for all test classes in the promptin package.
     * Provides common functionality for testing AI model interactions.
 */
@SpringBootTest
public class BaseTestClass {

    @Autowired
    protected ChatModel chatModel;

    String chat(String prompt) {
        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        Prompt promptToSend = promptTemplate.create();

        return chatModel.call(promptToSend).getResult().getOutput().getText();
    }

    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();

    }

}
