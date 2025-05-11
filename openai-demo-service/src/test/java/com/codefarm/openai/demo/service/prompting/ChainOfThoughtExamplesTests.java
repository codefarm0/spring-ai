package com.codefarm.openai.demo.service.prompting;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Chain of Thought (CoT) is a prompting technique that guides large language models
 * to break down complex problems into intermediate reasoning steps before providing a final answer.
 * 
 * This test class demonstrates how CoT prompting improves model accuracy compared to direct questioning,
 * by showing the model how to decompose problems into logical steps. The technique is particularly
 * effective for mathematical, temporal, and financial calculations.
 * 
 * @see <a href="https://arxiv.org/abs/2201.11903">Chain of Thought Paper</a>
 */
public class ChainOfThoughtExamplesTests extends BaseTestClass {

    @Autowired
    OpenAiChatProperties openAiChatProperties;

    @Test
    void testBasicMathProblem() {
        String prompt = """
                Q: Sarah has 8 books. She gives 3 books to her friend and then buys 5 new books. 
                How many books does Sarah have now?
                """;

        PromptTemplate promptTemplate = new PromptTemplate(prompt);

        ChatResponse response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testChainOfThoughtWithTimeProblem() {
        String chainOfThoughtPrompt = """
                Q: A train leaves Station A at 2:30 PM and arrives at Station B at 4:45 PM. 
                How long was the journey?
                
                A: Let's break this down step by step:
                1. From 2:30 PM to 3:30 PM is 1 hour
                2. From 3:30 PM to 4:30 PM is another 1 hour
                3. From 4:30 PM to 4:45 PM is 15 minutes
                4. Total time = 1 hour + 1 hour + 15 minutes = 2 hours and 15 minutes
                
                Q: A movie starts at 7:15 PM and ends at 9:45 PM. How long is the movie?
                """;

        PromptTemplate promptTemplate = new PromptTemplate(chainOfThoughtPrompt);

        ChatResponse response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testChainOfThoughtWithMoneyProblem() {
        String chainOfThoughtPrompt = """
                Q: John has $50. He spends $15 on lunch and $20 on groceries. Then he receives $30 from his friend. 
                How much money does John have now?
                
                A: Let's solve this step by step:
                1. Starting amount: $50
                2. Spent on lunch: $50 - $15 = $35
                3. Spent on groceries: $35 - $20 = $15
                4. Received from friend: $15 + $30 = $45
                Final amount: $45
                
                Q: Mary has $100. She spends $25 on clothes and $40 on electronics. Then she earns $50 from her part-time job. 
                How much money does Mary have now?
                """;

        PromptTemplate promptTemplate = new PromptTemplate(chainOfThoughtPrompt);

        ChatResponse response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testChainOfThoughtWithDistanceProblem() {
        String chainOfThoughtPrompt = """
                Q: A car travels 120 miles in 2 hours. What is its average speed in miles per hour?
                
                A: Let's solve this step by step:
                1. Total distance = 120 miles
                2. Total time = 2 hours
                3. Average speed = Total distance ÷ Total time
                4. Average speed = 120 miles ÷ 2 hours = 60 miles per hour
                
                Q: A cyclist covers 45 kilometers in 3 hours. What is their average speed in kilometers per hour?
                """;

        PromptTemplate promptTemplate = new PromptTemplate(chainOfThoughtPrompt);

        ChatResponse response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    void testConstrainedAnswer() {
        String prompt = """
                Q: A store sells shirts for $25 each. If they have a 20% discount, what is the final price? 
                Answer with just the number.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(prompt);

        ChatResponse response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getText());
    }
} 