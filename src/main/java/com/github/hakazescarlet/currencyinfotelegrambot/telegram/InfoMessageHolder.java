package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public final class InfoMessageHolder {

    private final String infoMessage;

    public InfoMessageHolder(@Value("classpath:infoMessage.txt") Resource resource) {
        try {
            this.infoMessage = resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InfoMessageReadingException("Failed to extract data from info message", e);
        }
    }

    public String get() {
        return infoMessage;
    }

    private static final class InfoMessageReadingException extends RuntimeException {

        public InfoMessageReadingException(String message, Exception e) {
            super(message, e);
        }
    }
}
