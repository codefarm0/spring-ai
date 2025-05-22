package com.codefarm.openai.demo.service.controller;

import com.codefarm.openai.demo.service.model.Answer;
import com.codefarm.openai.demo.service.model.Question;
import com.codefarm.openai.demo.service.service.VectorDemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vector")
public class VectorDemoController {

    private final VectorDemoService vectorDemoService;

    public VectorDemoController(VectorDemoService vectorDemoService) {
        this.vectorDemoService = vectorDemoService;
    }

    @PostMapping("/answer")
    public Answer post(@RequestBody Question question) {
        return vectorDemoService.getAnswer(question);
    }
}
