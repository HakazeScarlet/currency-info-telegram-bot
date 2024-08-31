package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScarletCurrencyConverterBot extends TelegramLongPollingBot {

    private static final Logger logger = LogManager.getLogger();

    public ScarletCurrencyConverterBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    // TODO: add validation
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = createButtons(chatId);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        if (update.hasMessage() && message.hasText()) {
//            String textFromUser = message.getText();
//
//            Long userId = message.getChatId();
//            String userFirstName = message.getFrom().getFirstName();
//
//            logger.info("[{}, {}] : {}", userId, userFirstName, textFromUser);
//
//            SendMessage sendMessage = SendMessage.builder()
//                .chatId(userId.toString())
//                .text("Hello, I've received your text: " + textFromUser)
//                .build();
//            try {
//                this.sendApiMethod(sendMessage);
//            } catch (TelegramApiException e) {
//                logger.error( "Exception when sending message: ", e);
//            }
//        } else {
//            logger.warn("Unexpected update from user");
//        }
//    }

    @Override
    public String getBotUsername() {
        return "ScarletCurrencyConverterBot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private SendMessage createButtons(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Help"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Test"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}