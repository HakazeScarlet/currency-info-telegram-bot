package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import net.fellbaum.jemoji.Emojis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public final class MessagesHolder {

    public static final String CONVERT_MESSAGE_EXAMPLE = "Add currencies to conversion and amount "
        + "\nExample "
        + Emojis.RIGHT_ARROW.getUnicode()
        + " USD EUR 1000";

    public static final String FAILED_RETRY_MESSAGE = "You have not had any conversion operations yet. " +
        "Please perform at least one operation using the \"Convert\" button.";

    public static final String MESSAGE_CAPTION = "You can help our project by donating to the QR-code to the crypto wallet "
        + "\n" + "or use specified link on the TRC-20 network :) "
        + "\n" + "TNbd9sLFerZfyH9P7FfoUsAkD6f5HTTUqj";

    public static final String FAVORITE_SAVING_MESSAGE = "Currencies added to your Favorite";

    private final String INFO_MESSAGE;

    public MessagesHolder(@Value("classpath:infoMessage.txt") Resource resource) {
        try {
            this.INFO_MESSAGE = resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InfoMessageReadingException("Failed to extract data from info message", e);
        }
    }

    public String get() {
        return INFO_MESSAGE;
    }

    private static final class InfoMessageReadingException extends RuntimeException {

        public InfoMessageReadingException(String message, Exception e) {
            super(message, e);
        }
    }
}
