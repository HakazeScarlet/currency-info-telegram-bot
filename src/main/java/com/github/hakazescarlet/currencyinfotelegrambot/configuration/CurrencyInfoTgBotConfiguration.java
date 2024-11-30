package com.github.hakazescarlet.currencyinfotelegrambot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableCaching
@ComponentScan
public class CurrencyInfoTgBotConfiguration {

    public static final String CACHE_NAME = "currency-rates-cache";

    // TODO: handle TelegramApiException
    @Bean
    public TelegramBotsApi createTelegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS);
    }

    @Bean
    public CacheManager createCacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(CACHE_NAME);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    // TODO: extract to envs
    @Bean
    public MongoClient createMongoClient() {
        try {
            CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            MongoCredential scramSha1Credential = MongoCredential.createScramSha1Credential("admin", "users_db", "pass".toCharArray());

            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .credential(scramSha1Credential)
                .codecRegistry(pojoCodecRegistry)
                .build();

            return MongoClients.create(settings);
        } catch (Exception exception) {
            // TODO: handle custom exception
            throw new RuntimeException(exception);
        }
    }
}