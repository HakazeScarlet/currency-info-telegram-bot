package com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class FavoriteInfoRepository {

    private static final String USERS_DB = "users_db";
    private static final String USERS_FAVORITE_COLLECTION = "users_favorite";

    private final MongoClient mongoClient;

    public FavoriteInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(FavoriteInfo favoriteInfo) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> collection = database.getCollection(USERS_FAVORITE_COLLECTION);

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);

        Bson filter = eq("_id", favoriteInfo.getUserId());
        Bson update = Updates.combine(
            Updates.set("№", favoriteInfo.getItem()),
            Updates.set("current", favoriteInfo.getCurrent()),
            Updates.set("target", favoriteInfo.getTarget())
            );

        collection.updateOne(filter, update, updateOptions);
    }
}
