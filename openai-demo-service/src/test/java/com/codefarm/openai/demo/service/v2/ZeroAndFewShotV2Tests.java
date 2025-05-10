package com.learn.openai.demo.service.promptin.v2;

import com.learn.openai.demo.service.promptin.BaseTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

/**
 * This test class explores advanced prompting techniques for AI models: zero-shot and few-shot learning.
 * 
 * Zero-shot learning enables AI models to handle tasks they weren't explicitly trained for,
 * by leveraging their general knowledge and pattern recognition abilities. For example,
 * classifying text into categories without seeing examples of those categories.
 * 
 * Few-shot learning improves model performance by providing a small set of examples
 * (typically 1-5) that demonstrate the desired input-output pattern. This helps
 * the model better understand the task requirements and expected response format.
 * 
 * The class demonstrates:
 * - Zero-shot classification and analysis
 * - Few-shot pattern recognition
 * - Prompt engineering for consistent outputs
 * - Comparison of zero-shot vs few-shot accuracy
 * 
 * These techniques are crucial for getting reliable results from AI models
 * while minimizing the need for task-specific training data.
 */
@SpringBootTest
public class ZeroAndFewShotV2Tests extends BaseTestClass {

    @Autowired
    OpenAiChatProperties openAiChatProperties;

    String review = """
            I recently purchased the latest MacBook Pro M3 Max, and I have to say, I'm thoroughly impressed with its performance. The M3 chip is a game-changer, handling my development workload with ease. I can run multiple Docker containers, VS Code, and Chrome with dozens of tabs without any lag.
            
            The build quality is exceptional, with the aluminum chassis feeling premium and durable. The keyboard is a joy to type on, and the trackpad is the best I've used on any laptop. The display is stunning, with perfect colors and excellent brightness.
            
            Battery life is outstanding - I can get through a full day of development work without needing to plug in. The new MagSafe charging is convenient, and the USB-C ports provide excellent connectivity options.
            
            However, there are a few minor issues. The notch in the display can be distracting when working with multiple windows, and the price point is quite high. Also, the lack of upgradeability means you need to choose your configuration carefully upfront.
            
            Overall, this is the best development machine I've ever used, and the performance justifies the premium price tag for professional users.""";

    String prompt = """
            Analyze the following product review and provide:
            1. A list of emotions expressed by the reviewer
            2. Key positive points
            3. Key negative points
            4. Overall sentiment score (1-10)
            
            Review: ```{review}```
            """;

    @Test
    void zeroShotPromptTest() {
        for (int i = 0; i < 3; i++) {
            PromptTemplate promptTemplate = new PromptTemplate(prompt,
                    Map.of("review", UUID.randomUUID() + "\n" + review));

            ChatResponse response = chatModel.call(promptTemplate.create());

            System.out.println("#################################\n");
            System.out.println(response.getResult().getOutput().getText());
        }
    }

    @Test
    void zeroShotPromptTestWithModelOptions() {
        OpenAiChatOptions openAiChatOptions = new OpenAiChatOptions.Builder(openAiChatProperties.getOptions())
                .temperature(0.1)
                .model("gpt-4-turbo-preview")
                .build();

        for (int i = 0; i < 3; i++) {
            PromptTemplate promptTemplate = new PromptTemplate(prompt,
                    Map.of("review", UUID.randomUUID() + "\n" + review));

            Prompt prompt = new Prompt(promptTemplate.createMessage(), openAiChatOptions);

            ChatResponse response = chatModel.call(prompt);

            System.out.println("#################################\n");
            System.out.println(response.getResult().getOutput().getText());
        }
    }

    String codingPrompt = """
            A "codebase" is a collection of source code files that make up a software project. An example of a sentence that uses
            the word codebase is:
            
            The team spent weeks refactoring the legacy codebase to improve maintainability.
            
            To "refactor" means to restructure existing code without changing its external behavior. An example of a sentence that uses
            the word refactor is:
            """;

    @Test
    void testCodingPromptFewShotTest() {
        PromptTemplate promptTemplate = new PromptTemplate(codingPrompt);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String developerPrompt = """
            Sarah is a full-stack developer who enjoys working with modern JavaScript frameworks and cloud technologies.
            
            What are 5 projects Sarah should consider building to enhance her portfolio?
            """;

    @Test
    void testDeveloperFewShotTest() {
        PromptTemplate promptTemplate = new PromptTemplate(developerPrompt);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String apiPrompt = """
            GET /users = fetchUsers
            POST /users = createUser
            PUT /users/{id} = updateUser
            
            What is DELETE /users/{id}?
            """;

    @Test
    void testApiPromptFewShotTest() {
        PromptTemplate promptTemplate = new PromptTemplate(apiPrompt);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    @Test
    void aiHallucinationTest() {
        Prompt prompt = new Prompt("Write a product description for the new 'Enterprise Edition' " +
                "Quantum Computing Development Kit by TechCorp.");

        System.out.println(chatModel.call(prompt).getResult().getOutput().getText());
    }
} 