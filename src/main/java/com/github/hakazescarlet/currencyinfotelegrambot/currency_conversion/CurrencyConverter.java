package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconCurrencyApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconExchangeRatesHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConverter {

    private final BeaconCurrencyApiProvider beaconCurrencyApiProvider;

    public CurrencyConverter(BeaconCurrencyApiProvider beaconCurrencyApiProvider) {
        this.beaconCurrencyApiProvider = beaconCurrencyApiProvider;
    }

    public BigDecimal convert(PairHolder pairHolder, BigDecimal amount) {
        // TODO: handle case when one of three parameters will be null (do it after telegram logic writing)
        BeaconExchangeRatesHolder beaconExchangeRatesHolder = beaconCurrencyApiProvider.getRates(pairHolder.getCurrent());
        Map<String, Double> conversionRates = beaconExchangeRatesHolder.getRates();
        Double currencyRate = conversionRates.get(pairHolder.getTarget());
        return BigDecimal.valueOf(currencyRate).multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }
}