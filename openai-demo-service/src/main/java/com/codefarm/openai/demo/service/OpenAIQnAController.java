package com.codefarm.openai.demo.service;

import com.codefarm.openai.demo.service.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIQnAController {

    private static final Logger log = LoggerFactory.getLogger(OpenAIQnAController.class);
    private final OpenAiService openAiService;

    public OpenAIQnAController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/ask")
    String ask(@RequestBody String question) {
        log.info("getting the answer of question: " + question);
        return openAiService.getAnswer(question);
    }
}
