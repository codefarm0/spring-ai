package com.codefarm.openai.demo.service;

import com.codefarm.openai.demo.service.model.*;
import com.codefarm.openai.demo.service.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/capital")
    CapitalResponse getCapital(@RequestBody CapitalRequest capitalRequest) {
        log.info("getting the capital of country: " + capitalRequest);
        return openAiService.getCapital(capitalRequest);
    }

    @PostMapping("/capital/info")
    CapitalResponse getCapitalWithInfo(@RequestBody CapitalRequest capitalRequest) {
        log.info("getting the capital of country: " + capitalRequest);
        return openAiService.getCapitalWithInfo(capitalRequest);
    }

    @PostMapping("/capital/json")
    CapitalResponse getCapitalWithJson(@RequestBody CapitalRequest capitalRequest) {
        log.info("getting the capital of country: " + capitalRequest);
        return openAiService.getCapitalWithJson(capitalRequest);
    }

    @PostMapping("/capital/json/binding")
    CapitalResponseV2 getCapitalWithJsonBinding(@RequestBody CapitalRequest capitalRequest) {
        log.info("getting the capital of country: " + capitalRequest);
        return openAiService.getCapitalWithJsonBinding(capitalRequest);
    }
}
