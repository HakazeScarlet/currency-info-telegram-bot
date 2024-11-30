package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

public class FavoritePair {

    @BsonProperty(value = "currency_pair")
    public PairHolder pairHolder;

    public LocalDateTime localDateTime;

    public PairHolder getPairHolder() {
        return pairHolder;
    }

    public void setPairHolder(PairHolder pairHolder) {
        this.pairHolder = pairHolder;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
