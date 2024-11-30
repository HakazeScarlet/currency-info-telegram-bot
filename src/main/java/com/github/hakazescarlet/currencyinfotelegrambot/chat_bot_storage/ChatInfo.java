package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;

public class ChatInfo {

    private final Long id;
    private final PairHolder pairHolder;

    public ChatInfo(Long id, PairHolder pairHolder) {
        this.id = id;
        this.pairHolder = pairHolder;
    }

    public Long getId() {
        return id;
    }

    public PairHolder getPairHolder() {
        return pairHolder;
    }
}