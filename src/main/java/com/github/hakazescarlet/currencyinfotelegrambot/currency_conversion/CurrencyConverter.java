package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconExchangeRatesHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.ConversionRatesApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.CurrencyBeaconApiProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConverter {

    private final ConversionRatesApiProvider currencyConverterApiProvider;
    private final CurrencyBeaconApiProvider currencyBeaconApiProvider;

    public CurrencyConverter(ConversionRatesApiProvider currencyConverterApiProvider, CurrencyBeaconApiProvider currencyBeaconApiProvider) {
        this.currencyConverterApiProvider = currencyConverterApiProvider;
        this.currencyBeaconApiProvider = currencyBeaconApiProvider;
    }

    public BigDecimal convert(String current, String target, BigDecimal amount) {
        // TODO: handle case when one of three parameters will be null (do it after telegram logic writing)
//        ConversionRatesHolder holder = currencyConverterApiProvider.getExchangeRate(current);
//        Map<String, Double> conversionRates = holder.getConversionRates();
//        return BigDecimal.valueOf(conversionRates.get(target)).multiply(amount);

        BeaconExchangeRatesHolder beaconExchangeRatesHolder = currencyBeaconApiProvider.getCurrencyBeaconExchangeRates(current);
        Map<String, Double> conversionRates = beaconExchangeRatesHolder.getRates();
        return BigDecimal.valueOf(conversionRates.get(target)).multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }
}