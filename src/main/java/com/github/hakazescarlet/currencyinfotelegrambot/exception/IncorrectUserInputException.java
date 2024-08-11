package com.github.hakazescarlet.currencyinfotelegrambot.exception;

public final class IncorrectUserInputException extends RuntimeException {

    public IncorrectUserInputException(String message, Exception e) {
        super(message, e);
    }
}
