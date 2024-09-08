package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {
    
    public CurrencyConversionBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    // TODO: add validation
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createButtons(chatId);
        sendMessage.setText(message.getText());
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new MessageSendException("Sending message build failed");
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

    private SendMessage createButtons(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Convert " + Emojis.CURRENCY_EXCHANGE.getUnicode()));
        keyboardFirstRow.add(new KeyboardButton("Favorite " + Emojis.STAR.getUnicode()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Retry last " + Emojis.RECYCLING_SYMBOL_UNQUALIFIED.getUnicode()));
        keyboardSecondRow.add(new KeyboardButton("Swap current " + Emojis.CLOCKWISE_VERTICAL_ARROWS.getUnicode()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Donate " + Emojis.MONEY_WITH_WINGS.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton("Help " + Emojis.CLIPBOARD.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton("Feedback " + Emojis.MEMO.getUnicode()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(new KeyboardButton("Cancel " + Emojis.PROHIBITED.getUnicode()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private final class MessageSendException extends RuntimeException {
        public MessageSendException(String message) {
            super(message);
        }
    }
}