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
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam Double amount) {
        BigDecimal result = currencyConverter.convert(from, to, BigDecimal.valueOf(amount));
        return ResponseEntity.ok(result);
    }
}