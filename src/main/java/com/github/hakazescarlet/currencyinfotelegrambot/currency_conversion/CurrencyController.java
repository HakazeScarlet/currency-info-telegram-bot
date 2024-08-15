package com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.currency_api.CurrencyBeaconApiProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class CurrencyController {

    private final CurrencyConverter currencyConverter;
    private final CurrencyBeaconApiProvider currencyBeaconApiProvider;

    public CurrencyController(CurrencyConverter currencyConverter, CurrencyBeaconApiProvider currencyBeaconApiProvider) {
        this.currencyConverter = currencyConverter;
        this.currencyBeaconApiProvider = currencyBeaconApiProvider;
    }

    // TODO: add request parameters: current, target, amount
    // TODO: return result as SomeObj class wrapped to ResponseEntity class
    @GetMapping("/calc")
    public void convert() {
//        currencyConverter.convert("JPY", "EUR", 3654.0);
//        currencyBeaconApiProvider.convert("GBP", "USD", BigDecimal.valueOf(12345.0));
//        currencyBeaconApiProvider.getCurrencyBeaconExchangeRates("USD");
        System.out.println(currencyConverter.convert("GBP", "ZAR", BigDecimal.valueOf(1000.0)));
    }
}
