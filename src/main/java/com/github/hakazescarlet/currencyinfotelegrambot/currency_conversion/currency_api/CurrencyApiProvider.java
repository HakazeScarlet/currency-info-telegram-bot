package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakazescarlet.currencyinfotelegrambot.exception.IncorrectUserInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CurrencyApiProvider {

    private static final String CURRENCY_LAYER_API_KEY = System.getenv("CURRENCY_LAYER_API_KEY");

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public CurrencyApiProvider(ObjectMapper objectMapper, HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
    }

    public void get(String current, String target, Double amount) {
        URI uri = URI.create("http://api.currencylayer.com/convert?access_key="
            + CURRENCY_LAYER_API_KEY
            + "&from=" + current
            + "&to=" + target
            + "&amount=" + amount);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ExchangeRatesInfo exchangeRatesResponse = objectMapper.readValue(response.body(), ExchangeRatesInfo.class);
            // TODO: return result
        } catch (IOException e) {
            throw new IncorrectUserInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
