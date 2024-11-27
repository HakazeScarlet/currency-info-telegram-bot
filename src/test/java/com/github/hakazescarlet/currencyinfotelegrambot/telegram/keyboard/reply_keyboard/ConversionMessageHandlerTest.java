package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.PairHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionMessageHandlerTest {

    @Test
    void whenParsingMessageHasCurrentAndTarget_returnTrue() {
        String message = "1000,00 USD ➡ 950,25 EUR";

        ConversionMessageHandler conversionMessageHandler = new ConversionMessageHandler();

        PairHolder actual = conversionMessageHandler.parsePair(message);
        PairHolder expected = new PairHolder("USD", "EUR");

        assertEquals(expected, actual);
    }
}