package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CurrencyController {

    private final CurrencyConverter currencyConverter;

    public CurrencyController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    // TODO: add request parameters: current, target, amount
    // TODO: return result as SomeObj class wrapped to ResponseEntity class
    @GetMapping("/calc")
    public void convert() {
        currencyConverter.convert("UAH", "EUR", 1000.0);
    }
}
