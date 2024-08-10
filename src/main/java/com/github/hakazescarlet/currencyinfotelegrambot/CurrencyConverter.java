package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrencyConverter {

    private Map<String, Double> fromCurrency;
    private double value;
    private double toCurrency;

    private final ConventionRatesHolder conventionRatesHolder;

    public CurrencyConverter(ConventionRatesHolder conventionRatesHolder) {
        this.conventionRatesHolder = conventionRatesHolder;
    }

    public void convert() {
        fromCurrency = conventionRatesHolder.getConversionRatesHolder();

    }
}