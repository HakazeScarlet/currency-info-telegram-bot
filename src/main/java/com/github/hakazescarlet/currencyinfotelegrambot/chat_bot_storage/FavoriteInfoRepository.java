package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.UserChatInfo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class FavoriteInfoRepository {

    private static final String USERS_DB = "users_db";  //TODO: rename
    private static final String USERS_COLLECTION = "users_collection";
    private static final String USER_ID = "id";
    private static final String FAVORITE_PAIRS = "favorite_pairs";

    private final MongoClient mongoClient;

    public FavoriteInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoCollection<UserChatInfo> collection = mongoClient.getDatabase(USERS_DB)
            .getCollection(USERS_COLLECTION, UserChatInfo.class);

        Bson filterFields = eq(USER_ID, chatInfo.getId());


//        List<FavoritePair> list = new ArrayList<>();
//        FavoritePair favoritePair = new FavoritePair();
//        favoritePair.setPairHolder(new PairHolder("5555gggg", "11111tttt"));
//        favoritePair.setLocalDateTime(LocalDateTime.now());
//        list.add(favoritePair);
//
//        Bson update = Updates.set(FAVORITE_PAIRS, list);
//
//        collection.updateMany(filterFields, update);
        // TODO: сохранять данные в таблицу Favorite. Колонки id, current, target, date_time (найти тип данных для монго - DateTime)
        // TODO: делать проверку на количество валютных пар в таблице Favorite (5) перед записью новой
        // TODO: если их уже 5, то перезаписать самую старую по дате

//        UpdateOptions updateOptions = new UpdateOptions();
//        updateOptions.upsert(true);

//        Bson filter = eq(USER_ID, chatInfo.getId());
//        collection.updateOne(filter, update, updateOptions);
    }
}
