package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

@Repository
public class RetryLastInfoRepository {

    private static final String USERS_DB = "users_db";  // TODO: rename
    private static final String USERS_COLLECTION = "users_collection";
    private static final String ID_FIELD = "id";
    private static final String CURRENT_FIELD = "current";
    private static final String TARGET_FIELD = "target";

    private final MongoClient mongoClient;

    public RetryLastInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> collection = database.getCollection(USERS_COLLECTION);

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);

        Bson filter = eq(ID_FIELD, chatInfo.getId());
        Bson update = Updates.combine(
            Updates.set(CURRENT_FIELD, chatInfo.getPairHolder().getCurrent()),
            Updates.set(TARGET_FIELD, chatInfo.getPairHolder().getTarget())
        );

        collection.updateOne(filter, update, updateOptions);
    }

    public PairHolder retrieve(Long chatId) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> chatInfoCollection = database.getCollection(USERS_COLLECTION);

        Bson searchFilter = Filters.eq(ID_FIELD, chatId);

        Bson fields = fields(include(CURRENT_FIELD, TARGET_FIELD), excludeId());
        Document document = chatInfoCollection.find(searchFilter)
            .projection(fields)
            .first();

        if (document == null) {
            return null;
        }

        return new PairHolder(
            (String) document.get(CURRENT_FIELD),
            (String) document.get(TARGET_FIELD)
        );
    }
}
