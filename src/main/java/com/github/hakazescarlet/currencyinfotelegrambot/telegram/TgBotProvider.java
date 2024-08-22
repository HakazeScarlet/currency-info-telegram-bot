package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TgBotProvider {

    private static final String BOT_TOKEN = System.getenv("TG_BOT_TOKEN");


    public void registerBot() {
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(BOT_TOKEN, new ScarletCurrencyConverterBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
