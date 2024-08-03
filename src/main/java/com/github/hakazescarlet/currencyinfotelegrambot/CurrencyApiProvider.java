package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CurrencyApiProvider {

    private static final String API_KEY = System.getenv("CURRENCY_LAYER_API_KEY");

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void calculate(String from, String to, double amount) {
        URI uri = null;
        try {
            uri = new URI("http://api.currencylayer.com/convert?access_key="
                    + API_KEY
                    + "&from=" + from
                    + "&to=" + to
                    + "&amount=" + amount);
        } catch (URISyntaxException e) {
            throw new IncorrectURIException("Please, check the correctness of the URI", e);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            throw new IncorrectUserInputException("Please, check the correctness of the input data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}

final class IncorrectURIException extends RuntimeException {


    public IncorrectURIException(String message, Exception e) {
        super(message, e);
    }
}

final class IncorrectUserInputException extends RuntimeException {

    public IncorrectUserInputException(String message, Exception e) {
        super(message, e);
    }
}
