package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.KeyboardBuilder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class StartButtonAction implements ButtonAction<SendMessage> {

    private final KeyboardBuilder keyboardBuilder;
    private final MessagesHolder messagesHolder;

    public StartButtonAction(KeyboardBuilder keyboardBuilder, MessagesHolder messagesHolder) {
        this.keyboardBuilder = keyboardBuilder;
        this.messagesHolder = messagesHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().equals("/start");
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendMessage sendMessage = keyboardBuilder.createReplyKeyboard(message.getChatId());
        sendMessage.setText(messagesHolder.get());

        botApiMethod.accept(sendMessage);
    }
}
