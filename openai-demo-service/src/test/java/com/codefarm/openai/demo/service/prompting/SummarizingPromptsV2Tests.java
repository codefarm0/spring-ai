package com.codefarm.openai.demo.service.prompting;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * This test class demonstrates techniques for text summarization using AI models.
 * It explores how to effectively condense long-form content into concise summaries
 * while preserving key information and maintaining readability.
 *
 * The class showcases:
 * - Book review summarization
 * - Length-constrained summary generation
 * - Technical content distillation
 * - E-commerce product description creation
 *
 * These tests help validate that the AI model can accurately capture
 * the essence of longer texts while adhering to specific length
 * and format requirements.
 */
@SpringBootTest
public class SummarizingPromptsV2Tests extends BaseTestClass {

    String review1 = """
            "The Pragmatic Programmer" by Andrew Hunt and David Thomas is a timeless masterpiece in software development literature. As a senior developer with over a decade of experience, I found this book to be an invaluable resource that goes beyond typical programming guides. Here's why I believe it's essential reading for anyone in the software industry.
                        
            Practical Wisdom:
            The authors don't just tell you what to do; they explain why certain practices matter and how they impact your daily work. The book is filled with real-world examples and practical tips that you can apply immediately. From code organization to debugging strategies, every chapter offers actionable insights.
                        
            Timeless Principles:
            Despite being written years ago, the principles in this book remain relevant today. The focus on clean code, DRY (Don't Repeat Yourself), and the importance of continuous learning are more crucial than ever in our rapidly evolving industry.
                        
            Comprehensive Coverage:
            The book covers everything from basic programming concepts to advanced topics like architecture and project management. It's structured in a way that both beginners and experienced developers can benefit from it.
                        
            Thought-Provoking:
            What sets this book apart is its ability to make you think differently about programming. It challenges common assumptions and encourages you to develop your own philosophy about software development.
                        
            In conclusion, "The Pragmatic Programmer" is more than just a book; it's a guide to becoming a better developer. Whether you're just starting your career or have been coding for years, this book will help you improve your craft and approach to software development.""";

    String reviewPrompt = """
        Your task is to generate a short summary for a technical book from an ecommerce site. The summary will be used for a
        web page selling the book.

        Summarize the review below, delimited by triple backticks, in at most 30 words.
    
        Review: ```{review}```
    """;

    @Test
    void testCreateDescriptionFromReview() {
        PromptTemplate promptTemplate = new PromptTemplate(reviewPrompt,
                Map.of("review", review1));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String review2 = """
            I recently completed "The Pragmatic Programmer" and it's transformed how I approach software development. The book's emphasis on practical solutions and real-world applications makes it stand out from other programming books. The authors' experience shines through in their ability to explain complex concepts in an accessible way.
                         
            The book's structure is particularly effective, with each chapter building on previous concepts while maintaining independence. This makes it easy to reference specific topics when needed. The tips and tricks section at the end of each chapter is especially valuable for quick reference.
                         
            What I appreciate most is how the book balances technical depth with practical advice. It doesn't just tell you what to do; it explains why certain practices matter and how they impact your work. The examples are clear and relevant, making it easy to apply the concepts to real projects.
                         
            While the book covers a lot of ground, I wish it had more examples of modern development practices like containerization and cloud deployment. However, the principles it teaches are universal and can be applied to any technology stack.
            """;

    String review3 = """
            An essential read for any serious developer. "The Pragmatic Programmer" provides timeless wisdom that remains relevant despite the rapid changes in technology. The authors' approach to problem-solving and their emphasis on practical solutions make this book a must-have reference.
            The book's strength lies in its ability to teach you how to think about programming, not just how to code. It covers everything from basic principles to advanced concepts, all while maintaining a focus on real-world applicability. The tips and tricks are particularly valuable, offering immediate ways to improve your development process.
            What sets this book apart is its emphasis on the human aspects of programming. It's not just about writing code; it's about being a better developer, working effectively in teams, and delivering quality software. The authors' experience and insights make this more than just a technical manual—it's a guide to professional growth.
            I recommend this book to developers at all levels. Whether you're just starting out or have years of experience, you'll find valuable insights that will help you improve your craft.
            """;

    String reviewPrompt3 = """
        Your task is to generate a comprehensive summary for a technical book from multiple reviews. The summary will be used for a
        web page selling the book. You will be given 3 reviews. Create the summary based on the reviews and
        include information from all 3 reviews.

        Summarize the reviews below, delimited by triple backticks, in at most 200 words.
    
        Review: ```{review}```
        
        Review 2: ```{review2}```
        
        Review 3: ```{review3}```
    """;

    @Test
    void testCreateDescriptionFrom3Reviews() {
        PromptTemplate promptTemplate = new PromptTemplate(reviewPrompt3,
                Map.of("review", review1, "review2", review2, "review3", review3));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String reviewPrompt4 = """
        Your task is to extract key points from multiple book reviews and create a concise summary. The summary will be used for a
        web page selling the book. You will be given 3 reviews. Create the summary based on the reviews and
        include information from all 3 reviews.

        Summarize the reviews below, delimited by triple backticks, in at most 200 words.
        Focus on practical benefits and key takeaways that would interest potential buyers.
    
        Review: ```{review}```
        
        Review 2: ```{review2}```
        
        Review 3: ```{review3}```
    """;

    @Test
    void testCreateDescriptionFrom3ReviewsExtract() {
        PromptTemplate promptTemplate =
                PromptTemplate.builder()
                .template(reviewPrompt4)
                .variables(Map.of("review", review1, "review2", review2, "review3", review3))
                        .build();
        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }
} 