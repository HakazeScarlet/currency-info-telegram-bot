package com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class ChatInfoRepository {

    private final MongoClient mongoClient;

    public ChatInfoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void save(ChatInfo chatInfo) {
        MongoDatabase database = mongoClient.getDatabase("users_db");
        MongoCollection<Document> collection = database.getCollection("users_conversions");
        collection.insertOne(new Document()     // TODO: search about insert or update
            .append("id", chatInfo.getId())
            .append("current", chatInfo.getCurrent())
            .append("target", chatInfo.getTarget()));
    }

    // TODO: implement retrieve method
}
