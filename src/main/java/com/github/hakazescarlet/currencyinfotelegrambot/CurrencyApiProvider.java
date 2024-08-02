package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CurrencyApiProvider {

    private static final String API_KEY = System.getenv("CURRENCY_LAYER_API_KEY");

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void calculate() {
        URI uri = null;
        try {
            uri = new URI("http://api.currencylayer.com/convert?access_key="
                    + API_KEY + "&from=USD&to=RUB&amount=400");
        } catch (URISyntaxException e) {
            throw new IncorrectURIException("", e);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

final class IncorrectURIException extends RuntimeException {

    public IncorrectURIException(String message, Exception e) {
        super(message, e);
    }
}
