package com.codefarm.openai.demo.service.prompting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * This test class demonstrates the use of AI models for inference tasks, particularly focusing on
 * sentiment analysis and topic detection from text data.
 *
 * Inference refers to the AI model's ability to draw conclusions and extract meaning from input text,
 * such as determining sentiment (positive/negative/neutral), identifying topics, and understanding
 * the underlying context and emotions in written content.
 *
 * The tests showcase how AI models can:
 * - Analyze product reviews to determine customer satisfaction
 * - Extract key themes and topics from text
 * - Identify emotional undertones in written feedback
 * - Process multilingual content for sentiment analysis
 */
@SpringBootTest
public class InferenceV2Tests extends BaseTestClass {

    String review1 = """
            I recently purchased the Tesla Model 3 and I am absolutely thrilled with my decision. The electric vehicle's performance is outstanding, with instant acceleration and smooth handling. The minimalist interior design is both elegant and functional, while the large touchscreen display makes controlling all features intuitive. The autopilot features are impressive, making highway driving much more relaxed. The range of 350 miles on a single charge has eliminated any range anxiety I initially had. The over-the-air updates keep adding new features, making the car feel new even after months of ownership. The environmental impact of driving an electric vehicle is an added bonus that makes me feel good about my choice.""";

    String review2 = """
            The new iPhone 15 Pro Max has been a game-changer for my photography business. The camera system is phenomenal, capturing stunning images in both bright and low-light conditions. The titanium build feels premium and the battery life easily lasts through my busiest days. The USB-C port has simplified my workflow, and the A17 Pro chip handles everything I throw at it without breaking a sweat. The Dynamic Island is more useful than I expected, and the always-on display is a nice touch. While it's definitely an investment, the quality and features make it worth every penny for a professional photographer like myself.""";

    String review3 = """
            The new restaurant in town was a complete disappointment. The food was cold, the service was slow, and the prices were way too high for what you get.""";

    String review4 = """
            Compré el nuevo MacBook Pro M3 y estoy muy decepcionado. La batería no dura lo que prometen, el sistema se calienta demasiado y el precio es excesivo para las características que ofrece. No lo recomendaría a nadie.""";

    String review5 = """
            I've been using the new AI-powered coding assistant for the past month, and I have mixed feelings. While it's incredibly helpful for generating boilerplate code and suggesting improvements, it sometimes makes basic mistakes that a junior developer wouldn't make. The subscription cost is reasonable, but the frequent disconnections and slow response times during peak hours are frustrating. The code explanations are thorough, but the documentation could be more comprehensive. It's a useful tool, but definitely not a replacement for human developers as some claim.""";

    String review6 = """
            The new electric scooter I purchased has been a nightmare from day one. The battery life is nowhere near the advertised 30 miles, barely making it to 15 miles on a full charge. The build quality is poor, with parts rattling and the brakes making concerning noises. Customer service has been unresponsive to my complaints, and the warranty process is unnecessarily complicated. I feel completely misled by the marketing claims and would strongly advise against purchasing this product.""";

    String sentimentPrompt = """
            Analyze the sentiment of the following product reviews and provide a concise summary of each review's key points.
            
            Review 1: ```{review1}```
            Review 2: ```{review2}```
            Review 3: ```{review3}```
            Review 4: ```{review4}```
            Review 5: ```{review5}```
            Review 6: ```{review6}```
            """;

    @DisplayName("Testing Product Review Sentiment")
    @Test
    void testingSentiment() {
        PromptTemplate promptTemplate = new PromptTemplate(sentimentPrompt,
                Map.of("review1", review1,
                        "review2", review2,
                        "review3", review3,
                        "review4", review4,
                        "review5", review5,
                        "review6", review6));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String emotionPrompt = """
            Identify the primary emotions expressed in each review and provide a brief analysis of the reviewer's experience.
            
            Review 1: ```{review1}```
            Review 2: ```{review2}```
            Review 3: ```{review3}```
            Review 4: ```{review4}```
            Review 5: ```{review5}```
            Review 6: ```{review6}```
            """;

    @DisplayName("Testing Review Emotions")
    @Test
    void testingEmotion() {
        PromptTemplate promptTemplate = new PromptTemplate(emotionPrompt,
                Map.of("review1", review1,
                        "review2", review2,
                        "review3", review3,
                        "review4", review4,
                        "review5", review5,
                        "review6", review6));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String frustrationTestPrompt = """
            Analyze each review for signs of frustration or disappointment. For each review, state the review number 
            and indicate whether the reviewer expresses frustration (yes/no).
            
            Respond using the following format:
            Review 1: yes/no
            Review 2: yes/no
            Review N: yes/no
            
            Review 1: ```{review1}```
            Review 2: ```{review2}```
            Review 3: ```{review3}```
            Review 4: ```{review4}```
            Review 5: ```{review5}```
            Review 6: ```{review6}```
            """;

    @DisplayName("Testing for Frustration")
    @Test
    void testingForFrustration() {
        PromptTemplate promptTemplate = new PromptTemplate(frustrationTestPrompt,
                Map.of("review1", review1,
                        "review2", review2,
                        "review3", review3,
                        "review4", review4,
                        "review5", review5,
                        "review6", review6));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }

    String techNews = """
            In a groundbreaking development, Google has announced the successful implementation of quantum supremacy in their latest quantum computer, achieving a calculation that would take traditional supercomputers thousands of years to complete. The breakthrough was made possible by their new 100-qubit processor, which demonstrated unprecedented stability and error correction capabilities.
                        
            Dr. Sarah Chen, lead researcher at Google Quantum AI, expressed her excitement about the milestone: "This achievement represents a significant step forward in quantum computing. We're now able to solve complex problems that were previously thought impossible, opening new possibilities in fields like cryptography, drug discovery, and climate modeling."
                        
            The announcement has sparked intense competition in the tech industry, with Microsoft and IBM both announcing their own quantum computing initiatives. Industry analysts predict that this development will accelerate the race for quantum computing dominance, with potential applications in artificial intelligence and machine learning.
                        
            However, some experts have raised concerns about the security implications of quantum computing. Dr. Michael Rodriguez, a cybersecurity expert, warned that "While this is an incredible scientific achievement, it also means we need to accelerate our efforts in developing quantum-resistant encryption methods to protect sensitive data."
                        
            The government has responded by announcing increased funding for quantum computing research and education programs, with a focus on developing a skilled workforce to support this emerging technology sector.
            """;

    String topicPrompt = """
            Identify the main topics discussed in the following technology news article, which is delimited by triple backticks.
            
            Provide the topics as a comma-separated list, with each topic being 1-3 words.
            
            Article: '''{techNews}'''
            """;

    @DisplayName("Inferring Tech News Topics")
    @Test
    void inferTopics() {
        PromptTemplate promptTemplate = new PromptTemplate(topicPrompt,
                Map.of("techNews", techNews));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getText());
    }
} 