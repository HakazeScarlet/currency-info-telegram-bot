package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

@Component
public class CurrencyApiProvider {

    private static final String API_KEY = "cur_live_3afXwqlcpeTMdLcN857JUUSWSfqJjJSxeGl1hKPZ";

    public void test() {
        URI uri = null;
        try {
            uri = new URI("https://api.currencyapi.com/v3/latest?apikey=" + API_KEY);
        } catch (URISyntaxException e) {
            throw new IncorrectURIException("", e);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }
}

final class IncorrectURIException extends RuntimeException {

    public IncorrectURIException(String message, Exception e) {
        super(message, e);
    }
}
