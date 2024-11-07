package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonCreator;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.InfoMessageHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class StartButtonAction implements ButtonAction<SendMessage> {

    private final ButtonCreator buttonCreator;
    private final InfoMessageHolder infoMessageHolder;

    public StartButtonAction(ButtonCreator buttonCreator, InfoMessageHolder infoMessageHolder) {
        this.buttonCreator = buttonCreator;
        this.infoMessageHolder = infoMessageHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().equals("/start");
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendMessage sendMessage = buttonCreator.create(message.getChatId());
        sendMessage.setText(infoMessageHolder.get());

        botApiMethod.accept(sendMessage);
    }
}
