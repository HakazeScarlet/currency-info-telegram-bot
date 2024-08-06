package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesResponse {

    @JsonProperty("result")
    private double finalCurrency;

    @JsonProperty("quote")
    private double currencyQuote;

    public double getFinalCurrency() {
        return finalCurrency;
    }

    public void setFinalCurrency(double finalCurrency) {
        this.finalCurrency = finalCurrency;
    }

    public double getCurrencyQuote() {
        return currencyQuote;
    }

    public void setCurrencyQuote(double currencyQuote) {
        this.currencyQuote = currencyQuote;
    }
}
