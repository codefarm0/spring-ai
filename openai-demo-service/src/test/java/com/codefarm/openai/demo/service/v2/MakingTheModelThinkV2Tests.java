package com.learn.openai.demo.service.promptin.v2;

import com.learn.openai.demo.service.promptin.BaseTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * This test class demonstrates techniques for making AI models perform multi-step reasoning tasks.
 * It explores how to structure prompts that require the model to break down complex problems
 * into discrete steps and show its work.
 *
 * The class showcases:
 * - Multi-step task decomposition
 * - Cross-language processing
 * - Data extraction and transformation
 * - Structured output generation
 *
 * These tests help validate that the AI model can follow a chain of logical steps
 * while maintaining accuracy across different types of operations.
 */
@SpringBootTest
public class MakingTheModelThinkV2Tests extends BaseTestClass {

    String story = """
        In a bustling tech startup, two developers, Alex and Sam, embarked on
        a mission to fix a critical bug before the product launch.
        As they worked through the night, debugging tirelessly, disaster
        struck—their main database server crashed, with Sam's recent changes
        potentially causing the issue. Though exhausted, the pair quickly
        implemented a fix and restored the system. Despite the setback,
        their determination remained strong, and they successfully
        deployed the update before the deadline.
        """;

    String prompt = """
            Perform the following actions:
            1 - Summarize the following text delimited by triple
            backticks with 1 sentence.
            2 - Translate the summary into Japanese.
            3 - List each name in the Japanese summary.
            4 - Output a json object that contains the following
            keys: japanese_summary, num_names.
            Separate your answers with line breaks.
            Text:
            ```{text}```
            """;

    @Test
    void testSteps() {
        PromptTemplate promptTemplate = new PromptTemplate(prompt,
                Map.of("text", story));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String prompt2Incorrect = """
            Determine if the student's solution is correct or not.
                        
            Question:
            I'm building a cloud infrastructure and I need
            help working out the costs.
            - Server costs $50 / hour
            - Storage costs $0.10 / GB per month
            - Network bandwidth costs $0.05 / GB
            - I have a fixed maintenance cost of $1000 per month
            
            What is the total cost for the first month of operations
            as a function of the number of hours and storage used.
                        
            Student's Solution:
            Let x be the number of hours and y be the storage in GB.
            Costs:
            1. Server cost: 50x
            2. Storage cost: 0.10y
            3. Network cost: 0.05y
            4. Maintenance cost: 1000
            Total cost: 50x + 0.10y + 0.05y + 1000 = 50x + 0.15y + 1000
            """;

    @Test
    void testIncorrectPrompt() {
        PromptTemplate promptTemplate = new PromptTemplate(prompt2Incorrect);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String prompt3Correct = """
            Your task is to determine if the student's solution is correct or not.
            To solve the problem do the following:
            - First, work out your own solution to the problem including the final total.
            - Then compare your solution to the student's solution and evaluate if the student's solution is correct or not.
            
            Don't decide if the student's solution is correct until you have done the problem yourself.
                        
            Use the following format:
            Question:
            ```question here```
            
            Student's solution:
            ```student's solution here```
            
            Actual solution:
            ```steps to work out the solution and your solution here```
            
            Is the student's solution the same as actual solution just calculated:
            ```yes or no```
            
            Student grade:
            ```correct or incorrect```
                        
            Question:
            ```
            I'm building a cloud infrastructure and I need help working out the costs.
            - Server costs $50 / hour
            - Storage costs $0.10 / GB per month
            - Network bandwidth costs $0.05 / GB
            - I have a fixed maintenance cost of $1000 per month
            
            What is the total cost for the first month of operations as a function of the number of hours and storage used.
            ```
            
            Student's solution:
            ```
            Let x be the number of hours and y be the storage in GB.
            Costs:
            1. Server cost: 50x
            2. Storage cost: 0.10y
            3. Network cost: 0.05y
            4. Maintenance cost: 1000
            Total cost: 50x + 0.10y + 0.05y + 1000 = 50x + 0.15y + 1000
            ```
            
            Actual solution:
            ```actual solution here```
            
            """;

    @Test
    void testCorrectPrompt() {
        PromptTemplate promptTemplate = new PromptTemplate(prompt3Correct);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String prompt4 = """
       You are an expert at solving reasoning problems. A cloud server is a virtual machine that runs in a data center. The server has a public IP address and can be accessed over the internet.
       
       Assume standard cloud computing principles. A developer deploys an application to a cloud server and configures it to run on port 8080. The server is placed behind a load balancer,
        which distributes traffic across multiple servers. The load balancer is configured to forward requests to port 8080 on the servers.
       Someone then changes the application to run on port 3000 instead. What happens to the traffic? Determine the flow of traffic in each step. Explain 
       why the traffic is affected the way it is.
       """;

    @Test
    void testCloudTrafficPrompt() {
        PromptTemplate promptTemplate = new PromptTemplate(prompt4);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }
} 