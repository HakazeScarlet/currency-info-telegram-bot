package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import lombok.SneakyThrows;
import net.fellbaum.jemoji.Emojis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {

    private final ButtonCreator buttonCreator;
    
    public CurrencyConversionBot(@Value("${telegram.bot.token}") String botToken, ButtonCreator buttonCreator) {
        super(botToken);
        this.buttonCreator = buttonCreator;
    }

    // TODO: add validation
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Map<String, ChatState> userInfoMap = new HashMap<>();
        if (message.getText().equals("/start")) {
            Long chatId = update.getMessage().getChatId();
            SendMessage sendMessage = buttonCreator.createButtons(chatId);
            sendMessage.setText("info message");
            sendApiMethod(sendMessage);
        } else {
            if (message.getText().equals("Convert " + Emojis.CURRENCY_EXCHANGE.getUnicode())) {

            } else if (message.getText().equals("Help " + Emojis.CLIPBOARD.getUnicode())) {

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Scarlet_Currency_Converter_Bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private final class MessageSendException extends RuntimeException {
        public MessageSendException(String message) {
            super(message);
        }
    }
}