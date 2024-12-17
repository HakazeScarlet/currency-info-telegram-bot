package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconCurrencyApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconExchangeRatesHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyConverter {

    public static final Set<String> CURRENCY_CODES = Currency.getAvailableCurrencies().stream()
        .map(currency -> currency.getCurrencyCode())
        .collect(Collectors.toSet());

    private final BeaconCurrencyApiProvider beaconCurrencyApiProvider;

    public CurrencyConverter(BeaconCurrencyApiProvider beaconCurrencyApiProvider) {
        this.beaconCurrencyApiProvider = beaconCurrencyApiProvider;
    }

    public BigDecimal convert(PairHolder pairHolder, Double amount) {
        PairHolder validPairHolder = CurrencyConverter.validateCurrencyPair(pairHolder);
        BeaconExchangeRatesHolder validRatesHolder = CurrencyConverter.validateRatesHolder(
            beaconCurrencyApiProvider.getRates(validPairHolder.getCurrent()),
            validPairHolder
        );

        Map<String, Double> conversionRates = validRatesHolder.getRates();
        Double currencyRate = conversionRates.get(validPairHolder.getTarget());
        return BigDecimal.valueOf(currencyRate)
            .multiply(BigDecimal.valueOf(amount))
            .setScale(2, RoundingMode.HALF_UP);
    }

    private static PairHolder validateCurrencyPair(PairHolder pairHolder) {
        String current = pairHolder.getCurrent();
        String target = pairHolder.getTarget();
        if (current != null && target != null) {
            if (CURRENCY_CODES.contains(current) && CURRENCY_CODES.contains(target)) {
                return pairHolder;
            }
            throw new CurrencyCodeException("Current or target currency code is incorrect");
        }
        throw new NoPairCurrencyException("Current or target currency is null");
    }

    private static BeaconExchangeRatesHolder validateRatesHolder(
        BeaconExchangeRatesHolder beaconExchangeRatesHolder,
        PairHolder pairHolder
    ) {
        if (beaconExchangeRatesHolder.getRates() != null) {
            if (beaconExchangeRatesHolder.getRates().containsKey(pairHolder.getTarget())) {
                return beaconExchangeRatesHolder;
            }
            throw new ConversionRatesNoCurrencyException("No rate for " + pairHolder.getTarget() + " currency");
        }
        throw new ConversionRatesIsNullException("Conversion rates map is null");
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

    public static final class ConversionRatesIsNullException extends RuntimeException {

        public ConversionRatesIsNullException(String message) {
            super(message);
        }
    }

    public static final class ConversionRatesNoCurrencyException extends RuntimeException {

        public ConversionRatesNoCurrencyException(String message) {
            super(message);
        }
    }
}