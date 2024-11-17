package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

public class CurrencyHolder {   //TODO: rename

    private final String current;
    private final String target;

    public CurrencyHolder(String current, String target) {
        this.current = current;
        this.target = target;
    }

    public String getCurrent() {
        return current;
    }

    public String getTarget() {
        return target;
    }
}
