package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConversionMessageHandler {

    private static final String SEPARATOR = "\s";

    private static final Set<String> CODES = Currency.getAvailableCurrencies().stream()
        .map(Currency::getCurrencyCode)
        .collect(Collectors.toSet());

    public String buildMessage(ConversionInfo conversionInfo, BigDecimal converted) {
        PairHolder pairHolder = conversionInfo.getPairHolder();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%.2f %s %s %s %s",
            conversionInfo.getAmount(),
            pairHolder.getCurrent(),
            Emojis.RIGHT_ARROW.getUnicode(),
            decimalFormat.format(converted),
            pairHolder.getTarget());
    }

    public PairHolder parsePair(String str) {
        String[] splittedMessage = str.split(SEPARATOR);

        for (String messagePart : splittedMessage) {
            if (CODES.contains(messagePart.toUpperCase())) {
                // TODO: write to data structure
            }
        }

        return null;
    }
}
