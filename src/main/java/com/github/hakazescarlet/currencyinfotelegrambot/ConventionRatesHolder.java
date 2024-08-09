package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConventionRatesHolder {

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRatesHolder = new LinkedHashMap<>();

    public Map<String, Double> getConversionRatesHolder() {
        return conversionRatesHolder;
    }

    public void setConversionRatesHolder(Map<String, Double> conversionRatesHolder) {
        this.conversionRatesHolder = conversionRatesHolder;
    }
}
