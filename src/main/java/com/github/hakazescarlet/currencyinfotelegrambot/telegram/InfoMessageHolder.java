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
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public String get() {
        return infoMessage;
    }
}
