package com.codefarm.openai.demo.service.prompting;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * This test class demonstrates techniques for providing clear and structured instructions to AI models.
 * It explores how different output formats (JSON, XML, YAML) affect the model's responses and tests
 * the model's ability to follow specific formatting requirements.
 *
 * The class showcases:
 * - Structured data format generation
 * - Clear instruction patterns
 * - Format-specific response handling
 *
 * These tests help validate that the AI model can accurately interpret and follow
 * detailed formatting instructions while maintaining data consistency.
 */
@SpringBootTest
public class GiveClearInstructionsV2Tests extends BaseTestClass {

    @Test
    void testGetJSON() {
        String prompt = """
                Generate a list of 4 made up electric vehicles. Provide them in a JSON format
                with the following attributes: make, model, year, battery_range, and color. Return the JSON string.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        ChatResponse response = chatModel.call(promptTemplate.create());
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testGetXML() {
        String prompt = """
                Generate a list of 4 made up electric vehicles. Provide them in a XML format
                with the following attributes: make, model, year, battery_range, and color. Return the XML string.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        ChatResponse response = chatModel.call(promptTemplate.create());
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testGetYAML() {
        String prompt = """
                Generate a list of 4 made up electric vehicles. Provide them in a YAML format
                with the following attributes: make, model, year, battery_range, and color. Return the YAML string.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        ChatResponse response = chatModel.call(promptTemplate.create());
        System.out.println(response.getResult().getOutput().getText());
    }

    // Template for processing instructions
    String directionsPrompt = """
            You will be provided with text delimited by triple quotes.
            If it contains a sequence of instructions,
            re-write those instructions in the following format:
                        
            Step 1 - ...
            Step 2 - ...
            Step N - ...
                        
            If the text does not contain a sequence of instructions, then simply write \\"No steps provided.\\"
                        
            \\"\\"\\"{text_1}\\"\\"\\"
            """;

    String cookPasta = """
        Making the perfect pasta is easy.
        First, bring a large pot of water to a rolling boil.
        Add a generous amount of salt to the water.
        Add the pasta and cook according to package instructions, usually 8-12 minutes.
        While the pasta cooks, prepare your favorite sauce in a separate pan.
        Drain the pasta, reserving a cup of pasta water.
        Combine the pasta with the sauce, adding pasta water as needed to create a silky texture.
        Top with fresh herbs and serve immediately.
        Enjoy!""";

    String bookDescription = """
            Book: The Innovator's Dilemma
            When Clayton Christensen first published The Innovator's Dilemma in 1997, he was a relatively unknown Harvard Business School professor. The book, which would go on to become one of the most influential business books of all time, introduced the concept of disruptive innovation.
                        
            Christensen's work explained why great companies fail when faced with disruptive technological change. He showed how even the most successful companies can do everything right and still lose market leadership when confronted with disruptive technological change.
                        
            The book has been cited by thousands of academic papers and has influenced business leaders worldwide. It remains a must-read for anyone interested in innovation and business strategy.""";

    @Test
    void testCookPasta() {
        PromptTemplate promptTemplate = new PromptTemplate(directionsPrompt);
        Prompt prompt = promptTemplate.create(Map.of("text_1", cookPasta));
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testBookDescription() {
        PromptTemplate promptTemplate = new PromptTemplate(directionsPrompt);
        Prompt prompt = promptTemplate.create(Map.of("text_1", bookDescription));
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testCookPastaAsGordonRamsay() {
        PromptTemplate promptTemplate = new PromptTemplate(directionsPrompt + "Give the directions using the tone of Gordon Ramsay");
        Prompt prompt = promptTemplate.create(Map.of("text_1", cookPasta));
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testCookPastaAsMasterChef() {
        PromptTemplate promptTemplate = new PromptTemplate(directionsPrompt + "Give the directions using the tone and style of a MasterChef judge");
        Prompt prompt = promptTemplate.create(Map.of("text_1", cookPasta));
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getResult().getOutput().getText());
    }
} 