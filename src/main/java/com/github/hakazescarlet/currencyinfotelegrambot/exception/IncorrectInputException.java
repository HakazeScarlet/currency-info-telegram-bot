package com.github.hakazescarlet.currencyinfotelegrambot.exception;

public final class IncorrectInputException extends RuntimeException {

    public IncorrectInputException(String message, Exception e) {
        super(message, e);
    }
}