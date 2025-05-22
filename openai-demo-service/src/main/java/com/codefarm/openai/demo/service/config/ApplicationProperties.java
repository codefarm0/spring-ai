package com.codefarm.openai.demo.service.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "vector.store.data")

public class ApplicationProperties {

    private String moviesVector;

    private List<Resource> moviesToLoad;

    public String getMoviesVector() {
        return moviesVector;
    }

    public void setMoviesVector(String moviesVector) {
        this.moviesVector = moviesVector;
    }

    public List<Resource> getMoviesToLoad() {
        return moviesToLoad;
    }

    public void setMoviesToLoad(List<Resource> moviesToLoad) {
        this.moviesToLoad = moviesToLoad;
    }
}
