package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionRatesHolder {

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates = new LinkedHashMap<>();

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}