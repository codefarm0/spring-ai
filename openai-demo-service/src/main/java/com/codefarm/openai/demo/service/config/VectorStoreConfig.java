package com.codefarm.openai.demo.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
public class VectorStoreConfig {

    private static final Logger log = LoggerFactory.getLogger(VectorStoreConfig.class);
    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, ApplicationProperties applicationProperties) {
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        File vectorStoreFile = new File(applicationProperties.getMoviesVector());

        // Create parent directory if it doesn't exist
        vectorStoreFile.getParentFile().mkdirs();

        if (vectorStoreFile.exists()) {
            store.load(vectorStoreFile);
        } else {
            log.info("vector store file not exists, loading new files");
            applicationProperties.getMoviesToLoad().forEach(document -> {
                log.info("load document --> {}", document);
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = TokenTextSplitter.builder()
                        .withChunkSize(300)
                        .withMaxNumChunks(400)
                        .build();
                List<Document> splitDocs = textSplitter.apply(documents);
                store.add(splitDocs);
            });
            store.save(vectorStoreFile);
        }
        return store;
    }
}
