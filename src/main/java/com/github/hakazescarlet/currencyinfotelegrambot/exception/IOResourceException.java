package com.github.hakazescarlet.currencyinfotelegrambot.exception;

public final class IOResourceException extends RuntimeException {

    public IOResourceException(String message, Exception e) {
        super(message, e);
    }
}