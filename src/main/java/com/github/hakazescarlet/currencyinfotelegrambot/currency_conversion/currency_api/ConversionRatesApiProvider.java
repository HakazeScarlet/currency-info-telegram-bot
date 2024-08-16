package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakazescarlet.currencyinfotelegrambot.exception.IOResourceException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConversionRatesApiProvider {

    private static final String EXCHANGE_RATE_API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ConversionRatesApiProvider(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public ConversionRatesHolder getExchangeRate(String currency) {
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/" +
            EXCHANGE_RATE_API_KEY +
            "/latest/" +
            currency);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        ConversionRatesHolder conversionRatesHolder = null;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            conversionRatesHolder = objectMapper.readValue(response.body(), ConversionRatesHolder.class);
        } catch (IOException e) {
            // TODO: create more generalized exception
            throw new IOResourceException("Invalid request form or unable to extract data from response", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return conversionRatesHolder;
    }
}