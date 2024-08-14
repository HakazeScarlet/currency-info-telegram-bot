package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakazescarlet.currencyinfotelegrambot.exception.IncorrectInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CurrencyBeaconApiProvider {

    private static final String CURRENCY_BEACON_API_KEY = System.getenv("CURRENCY_BEACON_API_KEY");

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public CurrencyBeaconApiProvider(ObjectMapper objectMapper, HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
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
            throw new IncorrectInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return beaconConvertMapper;
    }

    public BeaconExchangeRates getCurrencyBeaconExchangeRates(String baseCurrency) {
        URI uri = URI.create("https://api.currencybeacon.com/v1/latest?api_key="
            + CURRENCY_BEACON_API_KEY
            + "&base=" + baseCurrency);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        BeaconExchangeRates beaconExchangeRates = new BeaconExchangeRates();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            beaconExchangeRates = objectMapper.readValue(response.body(), BeaconExchangeRates.class);
        } catch (IOException e) {
            throw new IncorrectInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return beaconExchangeRates;
    }
}