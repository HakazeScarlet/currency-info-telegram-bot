package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesInfo {

    @JsonProperty("result")
    private double currency;

    @JsonProperty("info")
    private Info info;

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
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
        private double quote;

        public double getQuote() {
            return quote;
        }

        public void setQuote(double quote) {
            this.quote = quote;
        }
    }
}
