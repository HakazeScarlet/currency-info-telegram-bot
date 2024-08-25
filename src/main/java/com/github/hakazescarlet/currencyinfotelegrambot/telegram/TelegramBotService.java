package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBotService {

    private final TelegramBotsApi telegramBotsApi;

    // TODO: handle TelegramApiException
    public TelegramBotService(TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        this.telegramBotsApi = telegramBotsApi;

        telegramBotsApi.registerBot(new ScarletCurrencyConverterBot(System.getenv("TG_BOT_TOKEN")));
    }
}
