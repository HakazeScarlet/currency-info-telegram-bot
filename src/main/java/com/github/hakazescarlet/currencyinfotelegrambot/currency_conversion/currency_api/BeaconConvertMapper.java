package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeaconConvertMapper {

    @JsonProperty("value")
    private Double conversionRate;

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
