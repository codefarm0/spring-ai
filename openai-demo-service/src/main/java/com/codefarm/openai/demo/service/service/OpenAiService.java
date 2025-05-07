package com.codefarm.openai.demo.service.service;

import com.codefarm.openai.demo.service.model.Answer;
import com.codefarm.openai.demo.service.model.CapitalRequest;
import com.codefarm.openai.demo.service.model.CapitalResponse;
import com.codefarm.openai.demo.service.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAiService {

    private final Logger logger = LoggerFactory.getLogger(OpenAiService.class);
    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource capitalPrompt;

    @Value("classpath:templates/get-capital-with-info-prompt.st")
    private Resource capitalPromptWithInfo;

    @Value("classpath:templates/get-capital-with-json-prompt.st")
    private Resource capitalPromptWithJson;

    @Value("classpath:templates/get-capital-with-json-binding-prompt.st")
    private Resource capitalPromptWithJsonBinding;


    @Autowired
    private ObjectMapper objectMapper;

    public OpenAiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getAnswer(String question){
        PromptTemplate promptTemplate = new PromptTemplate(question);

        ChatResponse response = chatModel.call(promptTemplate.create());

        return response.getResult().getOutput().getText();
    }

    public CapitalResponse getCapital(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPrompt);
        Prompt prompt =
                promptTemplate.create(Map.of("country", capitalRequest.country()));

        ChatResponse response = chatModel.call(prompt);

        return new CapitalResponse(response.getResult().getOutput().getText());
    }

    public CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptWithInfo);
        Prompt prompt =
                promptTemplate.create(Map.of("country", capitalRequest.country()));

        ChatResponse response = chatModel.call(prompt);

        return new CapitalResponse(response.getResult().getOutput().getText());
    }

    public CapitalResponse getCapitalWithJson(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptWithJson);
        Prompt prompt =
                promptTemplate.create(Map.of("country", capitalRequest.country()));

        ChatResponse response = chatModel.call(prompt);

        logger.info("response in json - {}", response.getResult().getOutput().getText());

        try {
            JsonNode node = objectMapper.readTree(response.getResult().getOutput().getText());
            return new CapitalResponse(node.get("capital").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CapitalResponse getCapitalWithJsonBinding(CapitalRequest capitalRequest) {

        BeanOutputConverter<CapitalResponse> converter = new BeanOutputConverter<>(CapitalResponse.class);
        String format = converter.getFormat();
        logger.info("Json format - {}", format);
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptWithJsonBinding);
        Prompt prompt =
                promptTemplate.create(Map.of("country", capitalRequest.country(),
                        "format", format));

        ChatResponse response = chatModel.call(prompt);

        logger.info("response in json - {}", response.getResult().getOutput().getText());

        return converter.convert(response.getResult().getOutput().getText());
    }
}
