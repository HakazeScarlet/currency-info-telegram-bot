package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesResponce {

    @JsonAlias("result")
    private double finalCurrency;

    private double quote;

    public void readCurrencyInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
    }
}
