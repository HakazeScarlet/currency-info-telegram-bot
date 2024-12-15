package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconCurrencyApiProvider;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconExchangeRatesHolder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyConverterTest {

    @Test
    void whenUseCurrencyConverter_returnConvertedResult() {
        PairHolder pairHolder = new PairHolder("EUR", "USD");
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 0.95);

        BeaconExchangeRatesHolder beaconExchangeRatesHolder = new BeaconExchangeRatesHolder();
        beaconExchangeRatesHolder.setRates(rates);
        beaconExchangeRatesHolder.setBaseCurrency("EUR");

        BeaconCurrencyApiProvider currencyApiProviderMock = Mockito.mock(BeaconCurrencyApiProvider.class);
        Mockito.when(currencyApiProviderMock.getRates("EUR")).thenReturn(beaconExchangeRatesHolder);

        CurrencyConverter currencyConverter = new CurrencyConverter(currencyApiProviderMock);
        BigDecimal actual = currencyConverter.convert(pairHolder, 100.0);

        assertEquals(BigDecimal.valueOf(95.00).setScale(2, RoundingMode.HALF_UP), actual);
    }

    @Test
    void whenNoPairOfCurrencies_throwNoPairCurrencyException() {
        PairHolder pairHolder = new PairHolder("EUR", null);
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 1.0);

        BeaconExchangeRatesHolder beaconExchangeRatesHolder = new BeaconExchangeRatesHolder();
        beaconExchangeRatesHolder.setRates(rates);
        beaconExchangeRatesHolder.setBaseCurrency("EUR");

        BeaconCurrencyApiProvider currencyApiProviderMock = Mockito.mock(BeaconCurrencyApiProvider.class);
        Mockito.when(currencyApiProviderMock.getRates("EUR")).thenReturn(beaconExchangeRatesHolder);

        assertThrows(
            CurrencyConverter.NoPairCurrencyException.class,
            () -> new CurrencyConverter(currencyApiProviderMock).convert(pairHolder, 1000.0));
    }

    @Test
    void whenCurrenciesNotValid_throwCurrencyCodeException() {
        PairHolder pairHolder = new PairHolder("bvsdfg1234", "3412wtWGwwer");
        Map<String, Double> rates = new HashMap<>();
        rates.put("bvsdfg1234", 1.0);

        BeaconExchangeRatesHolder beaconExchangeRatesHolder = new BeaconExchangeRatesHolder();
        beaconExchangeRatesHolder.setRates(rates);
        beaconExchangeRatesHolder.setBaseCurrency("bvsdfg1234");

        BeaconCurrencyApiProvider currencyApiProviderMock = Mockito.mock(BeaconCurrencyApiProvider.class);
        Mockito.when(currencyApiProviderMock.getRates("bvsdfg1234")).thenReturn(beaconExchangeRatesHolder);

        CurrencyConverter currencyConverter = new CurrencyConverter(currencyApiProviderMock);

        assertThrows(
            CurrencyConverter.CurrencyCodeException.class,
            () -> currencyConverter.convert(pairHolder, 1000.0)
        );
    }
}