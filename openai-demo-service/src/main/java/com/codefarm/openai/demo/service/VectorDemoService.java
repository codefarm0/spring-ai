package com.learn.openai.demo.service.service;

import com.learn.openai.demo.service.model.Answer;
import com.learn.openai.demo.service.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorDemoService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    @Value("classpath:templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public VectorDemoService(ChatModel chatModel, SimpleVectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    public Answer getAnswer(Question question) {

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder().query(question.question()).topK(5).build()
        );

        List<String> cottentList = documents.stream().map(Document::getText).toList();
        cottentList.forEach(System.out::println);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(
                Map.of(
                        "input", question.question(),
                "documents", String.join(",", cottentList)
                )
        );

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getText());
    }
}
