package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.InfoMessageHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class HelpButtonAction implements ButtonAction<SendMessage> {

    private final InfoMessageHolder infoMessageHolder;

    public HelpButtonAction(InfoMessageHolder infoMessageHolder) {
        this.infoMessageHolder = infoMessageHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.HELP.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(infoMessageHolder.get());
        botApiMethod.accept(sendMessage);
    }
}
