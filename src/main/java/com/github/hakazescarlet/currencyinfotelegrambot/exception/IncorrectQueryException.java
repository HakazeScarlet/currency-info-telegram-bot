package com.github.hakazescarlet.currencyinfotelegrambot.exception;

public final class IncorrectQueryException extends RuntimeException {

    public IncorrectQueryException(String message, Exception e) {
        super(message, e);
    }
}