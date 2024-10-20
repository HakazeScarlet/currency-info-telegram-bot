package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakazescarlet.currencyinfotelegrambot.exception.IOResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class BeaconCurrencyApiProvider {

    private static final String CURRENCY_BEACON_API_KEY = System.getenv("CURRENCY_BEACON_API_KEY");

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final CacheManager cacheManager;

    @Autowired
    public BeaconCurrencyApiProvider(ObjectMapper objectMapper, HttpClient httpClient, CacheManager cacheManager) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.cacheManager = cacheManager;
    }

    public BeaconConvertMapper convert(String current, String target, BigDecimal amount) {
        URI uri = URI.create("https://api.currencybeacon.com/v1/convert?api_key="
            + CURRENCY_BEACON_API_KEY
            + "&from=" + current
            + "&to=" + target
            + "&amount=" + amount);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        BeaconConvertMapper beaconConvertMapper = new BeaconConvertMapper();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            beaconConvertMapper = objectMapper.readValue(response.body(), BeaconConvertMapper.class);
        } catch (IOException e) {
            throw new IOResourceException("Invalid request form or unable to extract data from response", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return beaconConvertMapper;
    }

//    public BeaconExchangeRatesHolder getExchangeRates(String baseCurrency) {
//        Cache cache = cacheManager.getCache(CurrencyInfoTgBotConfiguration.CACHE_NAME);
//        BeaconExchangeRatesHolder ratesHolder = cache.get(baseCurrency, BeaconExchangeRatesHolder.class);
//
//        if (ratesHolder == null || ratesHolder.getRates() == null) {
//            return getRates(baseCurrency);
//        }
//        return ratesHolder;
//    }

//    @Cacheable(cacheNames = CurrencyInfoTgBotConfiguration.CACHE_NAME, key = "baseCurrency")
    public BeaconExchangeRatesHolder getRates(String baseCurrency) {
        URI uri = URI.create("https://api.currencybeacon.com/v1/latest?api_key="
            + CURRENCY_BEACON_API_KEY
            + "&base=" + baseCurrency);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        BeaconExchangeRatesHolder beaconExchangeRatesHolder = new BeaconExchangeRatesHolder();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            beaconExchangeRatesHolder = objectMapper.readValue(response.body(), BeaconExchangeRatesHolder.class);
        } catch (IOException e) {
            throw new IOResourceException("Invalid request form or unable to extract data from response", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return beaconExchangeRatesHolder;
    }
}