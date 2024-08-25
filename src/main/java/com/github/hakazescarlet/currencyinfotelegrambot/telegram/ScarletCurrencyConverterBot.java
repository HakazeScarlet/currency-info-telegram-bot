package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ScarletCurrencyConverterBot extends TelegramLongPollingBot {

    public ScarletCurrencyConverterBot(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "ScarletCurrencyConverterBot666";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
