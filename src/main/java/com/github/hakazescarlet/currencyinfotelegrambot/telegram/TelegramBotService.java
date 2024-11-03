package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBotService {


    // TODO: handle TelegramApiException
    public TelegramBotService(
        TelegramBotsApi telegramBotsApi,
        CurrencyConversionBot currencyConversionBot
    ) throws TelegramApiException {
        telegramBotsApi.registerBot(currencyConversionBot);
    }
}