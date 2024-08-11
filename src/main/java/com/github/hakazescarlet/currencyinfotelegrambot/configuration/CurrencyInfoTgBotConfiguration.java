package com.github.hakazescarlet.currencyinfotelegrambot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
@ComponentScan
public class CurrencyInfoTgBotConfiguration {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }
}
