package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrencyConverter {

    private Double result;

    private final CurrencyConverterApiProvider currencyConverterApiProvider;

    public CurrencyConverter(CurrencyConverterApiProvider currencyConverterApiProvider) {
        this.currencyConverterApiProvider = currencyConverterApiProvider;
    }

    public void convert(String currency, String target, double amount) {
        ConventionRatesHolder holder = currencyConverterApiProvider.getExchangeRate(currency);
        Map<String, Double> conversionRates = holder.getConversionRates();
        conversionRates.get(target);
        System.out.println(result = conversionRates.get(target) * amount);
    }
}