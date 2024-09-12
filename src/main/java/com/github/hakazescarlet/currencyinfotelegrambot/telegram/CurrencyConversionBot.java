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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {
    
    public CurrencyConversionBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    // TODO: add validation
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Map<Long, String> currenciesConversionInfo = new HashMap<>();
        if (message.getText().equals("/start")) {
            Long chatId = update.getMessage().getChatId();
            SendMessage sendMessage = createButtons(chatId);
            sendMessage.setText("info message");
            sendApiMethod(sendMessage);
        } else {
            if (message.getText().equals("Convert " + Emojis.CURRENCY_EXCHANGE.getUnicode())) {
                SendMessage convertFirstMessage = new SendMessage();
                convertFirstMessage.setChatId(message.getChatId());
                convertFirstMessage.setText("Convert from ");
                sendApiMethod(convertFirstMessage);
                String fromCurrencyMessage = update.getMessage().getText();

                SendMessage convertSecondMessage = new SendMessage();
                convertSecondMessage.setChatId(message.getChatId());
                convertSecondMessage.setText("Convert to ");
                sendApiMethod(convertSecondMessage);
                String toCurrencyMessage = update.getMessage().getText();

                SendMessage convertThirdMessage = new SendMessage();
                convertThirdMessage.setChatId(message.getChatId());
                convertThirdMessage.setText("Amount of " + fromCurrencyMessage);
                sendApiMethod(convertThirdMessage);
                String amountMessage = update.getMessage().getText();

                currenciesConversionInfo.put(message.getChatId(), fromCurrencyMessage);
                currenciesConversionInfo.put(message.getChatId(), toCurrencyMessage);
                currenciesConversionInfo.put(message.getChatId(), amountMessage);
            } else if (message.getText().equals("Help " + Emojis.CLIPBOARD.getUnicode())) {
                InputStream info = CurrencyConversionBot.class.getResourceAsStream("infoMessage.txt");
                new SendMessage().setText(info.toString());
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