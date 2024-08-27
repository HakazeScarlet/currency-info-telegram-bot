package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ScarletCurrencyConverterBot extends TelegramLongPollingBot {

    private static final Logger logger = LogManager.getLogger();

    public ScarletCurrencyConverterBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    // TODO: add validation
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            String textFromUser = message.getText();

            Long userId = message.getChatId();
            String userFirstName = message.getFrom().getFirstName();

            logger.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

            SendMessage sendMessage = SendMessage.builder()
                .chatId(userId.toString())
                .text("Hello, I've received your text: " + textFromUser)
                .build();
            try {
                this.sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                logger.error( "Exception when sending message: ", e);
            }
        } else {
            logger.warn("Unexpected update from user");
        }
    }

    @Override
    public String getBotUsername() {
        return "ScarletCurrencyConverterBot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
