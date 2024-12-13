package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconCurrencyApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconExchangeRatesHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Map;

@Service
public class CurrencyConverter {

    private final BeaconCurrencyApiProvider beaconCurrencyApiProvider;

    public CurrencyConverter(BeaconCurrencyApiProvider beaconCurrencyApiProvider) {
        this.beaconCurrencyApiProvider = beaconCurrencyApiProvider;
    }

    public BigDecimal convert(PairHolder pairHolder, Double amount) {
        // TODO: handle case when one of three parameters will be null (do it after telegram logic writing)
        // TODO: add test pairHolder проверка на нулл
        PairHolder validPairHolder = CurrencyConverter.validatePairHolder(pairHolder);
        BeaconExchangeRatesHolder beaconExchangeRatesHolder = beaconCurrencyApiProvider.getRates(validPairHolder.getCurrent());
        // TODO: add test conversionRates проверка мапки на нулл
        Map<String, Double> conversionRates = beaconExchangeRatesHolder.getRates();
        // TODO: add test проверка на нулл если в conversionRates нет нужной валюты
        Double currencyRate = conversionRates.get(validPairHolder.getTarget());
        return BigDecimal.valueOf(currencyRate)
            .multiply(BigDecimal.valueOf(amount))
            .setScale(2, RoundingMode.HALF_UP);
    }

    public static final class NoPairCurrencyException extends RuntimeException {

        public NoPairCurrencyException(String message) {
            super(message);
        }
    }

    public static final class CurrencyCodeException extends RuntimeException {

        public CurrencyCodeException(String message) {
            super(message);
        }
    }

    private static PairHolder validatePairHolder(PairHolder pairHolder) {
        String current = pairHolder.getCurrent();
        String target = pairHolder.getTarget();
        if (current != null && target != null) {
            String currencyCodes = Currency.getAvailableCurrencies().toString();
            if (currencyCodes.contains(current) && currencyCodes.contains(target)) {
                return pairHolder;
            }
            throw new CurrencyCodeException("Current or target currency code is incorrect");
        }
        throw new NoPairCurrencyException("Current or target currency is null");
    }
}
