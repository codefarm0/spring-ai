package com.learn.openai.demo.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Slf4j
@Configuration
public class VectorStoreConfig {
    
    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        // Create parent directory if it doesn't exist
        vectorStoreFile.getParentFile().mkdirs();

        if (vectorStoreFile.exists()) {
            store.load(vectorStoreFile);
        } else {
            log.info("vector store file not exists, loading new files");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("load document --> {}", document);


                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(documents);
                store.add(splitDocs);
                try {
                    log.info("waiting for some time to load next document {}", document);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            store.save(vectorStoreFile);
        }
        return store;
    }
}
