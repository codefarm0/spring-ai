package com.codefarm.openai.demo.service.prompting;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * This test class demonstrates the use of system messages in AI chat interactions.
 * System messages provide context and role definition that shape how the AI model
 * responds to subsequent user prompts.
 *
 * The class showcases:
 * - Setting AI personas through system messages
 * - Role-based response generation
 * - Context-aware conversations
 * - Professional domain expertise simulation
 *
 * These tests help validate that system messages effectively guide the AI model's
 * behavior and response patterns while maintaining the desired expertise level
 * and communication style.
 */
@SpringBootTest
public class UsingSystemMessagesV2Tests extends BaseTestClass {

    @Test
    void techStartupGuideTest() {
        String systemPrompt = """
                You are a successful tech startup founder with experience in building and scaling multiple companies.
                Your role is to provide practical, actionable advice about the tech startup ecosystem.
                You focus on real-world experiences, common pitfalls, and proven strategies.
                You maintain a professional yet approachable tone, using concrete examples from your experience.
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate("What are the key challenges in building a tech startup in 2024?");
        Message userMessage = promptTemplate.createMessage();

        List<Message> messages = List.of(systemMessage, userMessage);

        Prompt prompt = new Prompt(messages);

        System.out.println(chatModel.call(prompt).getResult().getOutput().getText());
    }

    @Test
    void aiEthicistTest() {
        String systemPrompt = """
                You are an AI ethicist and researcher specializing in responsible AI development.
                You answer questions using a balanced, thoughtful approach that considers both technical and ethical implications.
                You have deep knowledge of AI safety, bias, and societal impact.
                You maintain a scholarly tone while making complex concepts accessible.
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate("What are the ethical considerations in deploying large language models?");
        Message userMessage = promptTemplate.createMessage();

        List<Message> messages = List.of(systemMessage, userMessage);

        Prompt prompt = new Prompt(messages);

        System.out.println(chatModel.call(prompt).getResult().getOutput().getText());
    }

    String cloudMigration = """
        Migrating to the cloud requires careful planning and execution.
        First, assess your current infrastructure and identify workloads suitable for migration.
        Create a detailed inventory of applications, data, and dependencies.
        Choose the right cloud provider and services based on your needs.
        Develop a migration strategy, considering factors like downtime tolerance and data transfer costs.
        Implement security measures and compliance controls.
        Test thoroughly in a staging environment before going live.
        Monitor performance and optimize resources after migration.
        """;

    @Test
    void asTechBloggerTest() {
        String systemPrompt = """
                You are a popular tech blogger known for making complex technical concepts accessible and engaging.
                Your writing style is conversational, uses analogies, and includes practical examples.
                You often use bullet points and clear headings to organize information.
                You maintain a balance between technical accuracy and readability.
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate(cloudMigration);
        Message userMessage = promptTemplate.createMessage();

        List<Message> messages = List.of(systemMessage, userMessage);

        Prompt prompt = new Prompt(messages);

        System.out.println(chatModel.call(prompt).getResult().getOutput().getText());
    }

    @Test
    void asDevOpsEngineerTest() {
        String systemPrompt = """
                You are a senior DevOps engineer with extensive experience in cloud infrastructure and automation.
                You speak with technical precision while maintaining clarity.
                You focus on practical implementation details, best practices, and real-world solutions.
                You often reference specific tools, technologies, and industry standards.
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate(cloudMigration);
        Message userMessage = promptTemplate.createMessage();

        List<Message> messages = List.of(systemMessage, userMessage);

        Prompt prompt = new Prompt(messages);

        System.out.println(chatModel.call(prompt).getResult().getOutput().getText());
    }
} 