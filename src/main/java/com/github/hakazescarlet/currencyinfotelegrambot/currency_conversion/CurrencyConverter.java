package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.ConversionRatesApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.ConversionRatesHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CurrencyConverter {

    private final ConversionRatesApiProvider conversionRatesApiProvider;

    public CurrencyConverter(ConversionRatesApiProvider conversionRatesApiProvider) {
        this.conversionRatesApiProvider = conversionRatesApiProvider;
    }

    public Double convert(String current, String target, Double amount) {
        // TODO: handle case when one of three parameters will be null (do it after telegram logic writing)
        ConversionRatesHolder holder = conversionRatesApiProvider.get(current);
        Map<String, Double> conversionRates = holder.getConversionRates();
        return conversionRates.get(target) * amount;
    }
}