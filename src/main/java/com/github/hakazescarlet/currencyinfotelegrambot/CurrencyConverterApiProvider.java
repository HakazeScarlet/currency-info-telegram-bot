package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakazescarlet.currencyinfotelegrambot.exception.IncorrectUserInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
public class CurrencyConverterApiProvider {

    private static final String CALCULATE_CURRENCIES_API_KEY = System.getenv("CALCULATE_CURRENCIES_API_KEY");

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConventionRatesHolder getExchangeRate(String fromCurrency) {
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/" +
            CALCULATE_CURRENCIES_API_KEY +
            "/latest/" +
            fromCurrency);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        ConventionRatesHolder conventionRatesHolder = null;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            conventionRatesHolder = objectMapper.readValue(body, ConventionRatesHolder.class);
//            Map<String, Double> conversionRates = conventionRatesHolder.getConversionRates();
//            System.out.println(conversionRates.get("RUB"));
        } catch (IOException e) {
            throw new IncorrectUserInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return conventionRatesHolder;
    }
}
