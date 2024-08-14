package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.ConversionRatesApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.ConversionRatesHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConverter {

    private final ConversionRatesApiProvider currencyConverterApiProvider;

    public CurrencyConverter(ConversionRatesApiProvider currencyConverterApiProvider) {
        this.currencyConverterApiProvider = currencyConverterApiProvider;
    }

    public BigDecimal convert(String current, String target, BigDecimal amount) {
        // TODO: handle case when one of three parameters will be null (do it after telegram logic writing)
        ConversionRatesHolder holder = currencyConverterApiProvider.getExchangeRate(current);
        //test comment
        Map<String, Double> conversionRates = holder.getConversionRates();
        return BigDecimal.valueOf(conversionRates.get(target)).multiply(amount);
    }
}