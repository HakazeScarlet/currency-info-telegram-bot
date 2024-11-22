package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class HelpButtonAction implements ButtonAction<SendMessage> {

    private final MessagesHolder messagesHolder;

    public HelpButtonAction(MessagesHolder messagesHolder) {
        this.messagesHolder = messagesHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.HELP.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendMessage sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .text(messagesHolder.get())
            .build();

        botApiMethod.accept(sendMessage);
    }
}
