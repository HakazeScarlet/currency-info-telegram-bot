package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {

    private final Map<Long, ChatState> chatStates = new HashMap<>();
    private final ButtonCreator buttonCreator;
    private final InfoMessageHolder infoMessageHolder;

    public CurrencyConversionBot(
        @Value("${telegram.bot.token}") String botToken,
        ButtonCreator buttonCreator,
        InfoMessageHolder infoMessageHolder
    ) {
        super(botToken);
        this.buttonCreator = buttonCreator;
        this.infoMessageHolder = infoMessageHolder;
    }

    // TODO: add validation
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = update.getMessage().getChatId();
        if (message.getText().equals("/start")) {
            SendMessage sendMessage = buttonCreator.create(chatId);
            sendMessage.setText(infoMessageHolder.get());
            sendApiMethod(sendMessage);
        } else {
            if (message.getText().contains(ButtonTitle.CONVERT.getTitle())
                || chatStates.containsKey(chatId)) {

                if (!chatStates.containsKey(chatId)) {
                    ChatState actionChatState = new ChatState();
                    actionChatState.setAction(ButtonTitle.CONVERT.getTitle());
                    actionChatState.setChatId(chatId);

                    chatStates.put(chatId, actionChatState);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Convert from");
                    sendApiMethod(sendMessage);

                    ChatState currentState = new ChatState();
                    currentState.setCurrent(message.getText());
                    currentState.setChatId(chatId);
                    chatStates.put(chatId, currentState);
                }

                if (chatStates.get(chatId).getAction().contains(ButtonTitle.CONVERT.getTitle())
                    && chatStates.get(chatId).getCurrent() != null) {

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Convert to");
                    sendApiMethod(sendMessage);

                    ChatState targetState = new ChatState();
                    targetState.setTarget(message.getText());
                    targetState.setChatId(chatId);
                    chatStates.put(chatId, targetState);
                }

            } else if (message.getText().contains(ButtonTitle.HELP.getTitle())) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(infoMessageHolder.get());
                sendApiMethod(sendMessage);
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
}