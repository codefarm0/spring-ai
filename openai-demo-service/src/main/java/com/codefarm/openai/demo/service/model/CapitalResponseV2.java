package com.codefarm.openai.demo.service.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CapitalResponseV2(@JsonPropertyDescription("This is the capital city") String capital,
                                @JsonPropertyDescription("This is the population of the country") String population,
                                @JsonPropertyDescription("This is the name of the national animal of the country") String nationalAnimal,
                                @JsonPropertyDescription("This is the per capita income of the country") String perCapitaIncome,
                                @JsonPropertyDescription("This is the population density of the country") String populationDensity) {
}
