package com.codefarm.openai.demo.service.service;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    private final ChatModel chatModel;

    public OpenAiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getAnswer(String question){
        PromptTemplate promptTemplate = new PromptTemplate(question);

        ChatResponse response = chatModel.call(promptTemplate.create());

        return response.getResult().getOutput().getText();
    }
}
