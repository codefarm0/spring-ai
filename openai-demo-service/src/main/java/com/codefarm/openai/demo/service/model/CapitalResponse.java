package com.codefarm.openai.demo.service.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CapitalResponse(@JsonPropertyDescription("This is the capital city") String capital) {
}
