package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

public class UserChatInfo {

    @BsonProperty("_id")
    public ObjectId _id;

    @BsonProperty("id")
    private Long id;

    @BsonProperty(value = "retry_last")
    public PairHolder pairHolder;

    @BsonProperty(value = "favorite_pairs")
    public List<FavoritePair> favoritePairs;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PairHolder getPairHolder() {
        return pairHolder;
    }

    public void setPairHolder(PairHolder pairHolder) {
        this.pairHolder = pairHolder;
    }

    public List<FavoritePair> getFavoritePairs() {
        return favoritePairs;
    }

    public void setFavoritePairs(List<FavoritePair> favoritePairs) {
        this.favoritePairs = favoritePairs;
    }
}
