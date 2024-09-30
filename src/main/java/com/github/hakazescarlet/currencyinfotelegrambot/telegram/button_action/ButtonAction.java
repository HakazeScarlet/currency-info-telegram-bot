package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

public interface ButtonAction {

    boolean isApplicable(Message message, Map<Long, ChatState> chatStates);

    void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod);
}
