package com.github.hakazescarlet.currencyinfotelegrambot.util;

import java.util.Currency;

public final class CurrencyUtil {

    public static final String SEPARATOR = "\s";

    private CurrencyUtil() {
        // hide public constructor
    }

    public static PairHolder parsePair(String str) {
        String[] favoriteCurrencies = str.split(SEPARATOR);

        for (int i = 0; i <= str.split(SEPARATOR).length; i++) {
            if (Currency.getAvailableCurrencies().toString().contains(favoriteCurrencies[i].toUpperCase())) {
                String current = favoriteCurrencies[0];
            } else if (Currency.getAvailableCurrencies().toString().contains(favoriteCurrencies[i].toUpperCase())) {
                String target = favoriteCurrencies[1];
            }
        }
        return new PairHolder("EUR", "USD");
    }
}
