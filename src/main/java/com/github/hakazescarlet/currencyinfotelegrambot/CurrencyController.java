package com.github.hakazescarlet.currencyinfotelegrambot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CurrencyController {

    private final CurrencyApiProvider currencyApiProvider;

    public CurrencyController(CurrencyApiProvider currencyApiProvider) {
        this.currencyApiProvider = currencyApiProvider;
    }

    @GetMapping("/calc")
    public void test() {
        currencyApiProvider.calculate("RUB", "USD", 400);
    }
}
