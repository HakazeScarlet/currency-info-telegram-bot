package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

public class PairHolder {

    private final String current;
    private final String target;

    public PairHolder(String current, String target) {
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