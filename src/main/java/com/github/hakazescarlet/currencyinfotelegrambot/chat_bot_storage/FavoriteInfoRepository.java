package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class FavoriteInfoRepository {

    private static final String USERS_DB = "users_db";  //TODO: rename
    private static final String USERS_COLLECTION = "users_collection";
    private static final String ID_FIELD = "id";
    private static final String CURRENT_FIELD = "favorite_current";
    private static final String TARGET_FIELD = "favorite_target";
    private static final String DATE_TIME = "date_time";

    private final MongoClient mongoClient;

    public FavoriteInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoDatabase database = mongoClient.getDatabase(USERS_DB);
        MongoCollection<Document> collection = database.getCollection(USERS_COLLECTION);

        // TODO: сохранять данные в таблицу Favorite. Колонки id, current, target, date_time (найти тип данных для монго - DateTime)
        // TODO: делать проверку на количество валютных пар в таблице Favorite (5) перед записью новой
        // TODO: если их уже 5, то перезаписать самую старую по дате

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);

        Bson filter = eq(ID_FIELD, chatInfo.getId());
        Bson update = Updates.inc(ID_FIELD, 5);
//        Bson update = Updates.combine(
//            Updates.set(CURRENT_FIELD, chatInfo.getCurrent()),
//            Updates.set(TARGET_FIELD, chatInfo.getTarget()),
//            Updates.set(DATE_TIME, LocalDateTime.now())
//        );

//        collection.updateOne(filter, update, updateOptions);
    }
}
