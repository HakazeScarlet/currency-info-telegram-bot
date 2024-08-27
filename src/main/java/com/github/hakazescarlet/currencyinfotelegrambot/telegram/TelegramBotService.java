package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBotService {

    private final TelegramBotsApi telegramBotsApi;
    private final ScarletCurrencyConverterBot scarletCurrencyConverterBot;

    // TODO: handle TelegramApiException
    public TelegramBotService(
        TelegramBotsApi telegramBotsApi,
        ScarletCurrencyConverterBot scarletCurrencyConverterBot
    ) throws TelegramApiException {

        this.telegramBotsApi = telegramBotsApi;
        this.scarletCurrencyConverterBot = scarletCurrencyConverterBot;

        telegramBotsApi.registerBot(new ScarletCurrencyConverterBot();
    }
}
