package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

public class ChatInfo {

    private final Long id;
    private final String current;
    private final String target;

    public ChatInfo(Long id, String current, String target) {
        this.id = id;
        this.current = current;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public String getCurrent() {
        return current;
    }

    public String getTarget() {
        return target;
    }
}