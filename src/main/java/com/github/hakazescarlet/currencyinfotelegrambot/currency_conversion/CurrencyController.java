package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.BeaconCurrencyApiProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class CurrencyController {

    private final CurrencyConverter currencyConverter;
    private final BeaconCurrencyApiProvider beaconCurrencyApiProvider;

    public CurrencyController(CurrencyConverter currencyConverter, BeaconCurrencyApiProvider beaconCurrencyApiProvider) {
        this.currencyConverter = currencyConverter;
        this.beaconCurrencyApiProvider = beaconCurrencyApiProvider;
    }

    // TODO: add request parameters: current, target, amount
    // TODO: return result as SomeObj class wrapped to ResponseEntity class
    @GetMapping("/calc")
    public void convert(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
//        currencyConverter.convert("JPY", "EUR", 3654.0);
//        currencyBeaconApiProvider.convert("GBP", "USD", BigDecimal.valueOf(12345.0));
//        currencyBeaconApiProvider.getCurrencyBeaconExchangeRates("USD");
        currencyConverter.convert("GBP", "ZAR", BigDecimal.valueOf(1000.0));
    }
}
