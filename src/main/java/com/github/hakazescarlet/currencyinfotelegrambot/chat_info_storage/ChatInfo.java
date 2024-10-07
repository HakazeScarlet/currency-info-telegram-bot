package com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage;

public class ChatInfo {

    private final String id;
    private final String current;
    private final String target;

    public ChatInfo(String id, String current, String target) {
        this.id = id;
        this.current = current;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public String getCurrent() {
        return current;
    }

    public String getTarget() {
        return target;
    }
}
