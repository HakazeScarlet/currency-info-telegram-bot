package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyController {

    private final CurrencyConverter currencyConverter;

    public CurrencyController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    @GetMapping("/convert")
    public ResponseEntity<BigDecimal> convert(
        @RequestParam String current,
        @RequestParam String target,
        @RequestParam Double amount
    ) {
        return ResponseEntity.ok(currencyConverter.convert(current, target, BigDecimal.valueOf(amount)));
    }
}