package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionMessageHandlerTest {

    @Test
    void whenParsingMessageHasCurrentAndTarget_returnTrue() {
        ConversionInfo conversionInfo = new ConversionInfo();
        conversionInfo.setPairHolder(new PairHolder("USD", "EUR"));
        String message = ConversionMessageHandler.buildMessage(conversionInfo, new BigDecimal(1000));

        PairHolder expected = new PairHolder("USD", "EUR");
        PairHolder actual = ConversionMessageHandler.parsePair(message);

        assertEquals(expected, actual);
    }
}