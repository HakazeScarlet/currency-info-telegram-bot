package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import net.fellbaum.jemoji.Emojis;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ConversionMessageHandler {

    public static final String SEPARATOR = "\s";

    public static String buildMessage(ConversionInfo conversionInfo, BigDecimal converted) {
        PairHolder pairHolder = conversionInfo.getPairHolder();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%.2f %s %s %s %s",
            conversionInfo.getAmount(),
            pairHolder.getCurrent(),
            Emojis.RIGHT_ARROW.getUnicode(),
            decimalFormat.format(converted),
            pairHolder.getTarget());
    }

    public static PairHolder parsePair(String str) {
        String[] splittedMessage = str.split(SEPARATOR);
        return new PairHolder(splittedMessage[1], splittedMessage[4]);
    }
}