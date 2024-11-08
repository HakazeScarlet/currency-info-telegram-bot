package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonCreator;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class StartButtonAction implements ButtonAction<SendMessage> {

    private final ButtonCreator buttonCreator;
    private final MessagesHolder messagesHolder;

    public StartButtonAction(ButtonCreator buttonCreator, MessagesHolder messagesHolder) {
        this.buttonCreator = buttonCreator;
        this.messagesHolder = messagesHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().equals("/start");
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendMessage sendMessage = buttonCreator.create(message.getChatId());
        sendMessage.setText(messagesHolder.get());

        botApiMethod.accept(sendMessage);
    }
}
