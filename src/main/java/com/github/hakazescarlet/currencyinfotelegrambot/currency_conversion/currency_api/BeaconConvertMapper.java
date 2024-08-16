package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeaconConvertMapper {

    @JsonProperty("value")
    private Double convertedCurrency;

    public Double getConvertedCurrency() {
        return convertedCurrency;
    }

    public void setConvertedCurrency(Double convertedCurrency) {
        this.convertedCurrency = convertedCurrency;
    }
}
