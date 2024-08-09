package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesInfo {

    @JsonProperty("result")
    private double finalCurrency;

    @JsonProperty("info")
    private Info info;

    public double getFinalCurrency() {
        return finalCurrency;
    }

    public void setFinalCurrency(double finalCurrency) {
        this.finalCurrency = finalCurrency;
    }

    public Info getCurrencyQuote() {
        return info;
    }

    public void setCurrencyQuote(Info info) {
        this.info = info;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Info {

        @JsonProperty("quote")
        private double currencyQuote;

        public double getCurrencyQuote() {
            return currencyQuote;
        }

        public void setCurrencyQuote(double currencyQuote) {
            this.currencyQuote = currencyQuote;
        }
    }
}
