package com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage;

public class FavoriteInfo {

    private final Long userId;
    private final int item;
    private final String current;
    private final String target;

    public FavoriteInfo(Long chatId, int item, String current, String target) {
        this.userId = chatId;
        this.item = item;
        this.current = current;
        this.target = target;
    }

    public Long getUserId() {
        return userId;
    }

    public int getItem() {
        return item;
    }

    public String getCurrent() {
        return current;
    }

    public String getTarget() {
        return target;
    }
}
