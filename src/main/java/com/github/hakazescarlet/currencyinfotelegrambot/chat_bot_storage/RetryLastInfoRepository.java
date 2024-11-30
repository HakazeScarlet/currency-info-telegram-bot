package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.UserChatInfo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

@Repository
public class RetryLastInfoRepository {

    private static final String USERS_DB = "users_db";  // TODO: rename
    private static final String USERS_COLLECTION = "users_collection";
    private static final String RETRY_LAST = "retry_last";
    private static final String USER_ID = "id";

    private final MongoClient mongoClient;

    public RetryLastInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoCollection<UserChatInfo> collection = mongoClient.getDatabase(USERS_DB)
            .getCollection(USERS_COLLECTION, UserChatInfo.class);

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);

        Bson filter = eq(USER_ID, chatInfo.getId());
        Bson updatable = Updates.set(RETRY_LAST, chatInfo.getPairHolder());
        collection.updateOne(filter, updatable, updateOptions);
    }

    public PairHolder retrieve(Long chatId) {
        MongoCollection<UserChatInfo> collection = mongoClient.getDatabase(USERS_DB)
            .getCollection(USERS_COLLECTION, UserChatInfo.class);

        Bson searchFilter = Filters.eq(USER_ID, chatId);
        Bson fields = fields(include(RETRY_LAST), excludeId());

        UserChatInfo userChatInfo = collection.find(searchFilter)
            .projection(fields)
            .first();

        if (userChatInfo == null) {
            return null;
        }

        return userChatInfo.getPairHolder();
    }
}
