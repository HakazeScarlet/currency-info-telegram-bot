package com.github.hakazescarlet.currencyinfotelegrambot;

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

    private static final String API_KEY = System.getenv("CURRENCY_LAYER_API_KEY");

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void convert(String from, String to, double amount) {
        URI uri = URI.create("http://api.currencylayer.com/convert?access_key="
            + API_KEY
            + "&from=" + from
            + "&to=" + to
            + "&amount=" + amount);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            ExchangeRatesInfo exchangeRatesResponse = objectMapper.readValue(body, ExchangeRatesInfo.class);
            System.out.println("Currency Quote: " +
                exchangeRatesResponse.getCurrencyQuote() +
                "\nFinal Currency: " +
                exchangeRatesResponse.getFinalCurrency());
        } catch (IOException e) {
            throw new IncorrectUserInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
