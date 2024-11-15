package com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

@Repository
public class ChatInfoRepository {

    private static final String USERS_DB = "users_db";
    private static final String USERS_CONVERSIONS_COLLECTION = "users_conversions";
    private final MongoClient mongoClient;

    public ChatInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> collection = database.getCollection(USERS_CONVERSIONS_COLLECTION);

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);

        Bson filter = eq("id", chatInfo.getId());
        Bson update = Updates.combine(
            Updates.set("current", chatInfo.getCurrent()),
            Updates.set("target", chatInfo.getTarget())
        );

        collection.updateOne(filter, update, updateOptions);
    }

    public CurrencyHolder retrieve(Long chatId) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> chatInfoCollection = database.getCollection(USERS_CONVERSIONS_COLLECTION);
        chatInfoCollection.find(eq("id", chatId));

        Bson userCurrents = Projections.fields(
            Projections.include("current", "target"));
        Projections.exclude("id");

        Document document = chatInfoCollection.find(gt("id", chatId))
            .projection(userCurrents)
            .first();

        if (document == null) {
            return null;
        }

        return new CurrencyHolder(
            document.get("current").toString(),
            document.get("target").toString()
        );
    }
}
