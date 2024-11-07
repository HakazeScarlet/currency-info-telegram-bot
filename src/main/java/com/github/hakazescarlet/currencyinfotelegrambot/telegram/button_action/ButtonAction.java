package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

public interface ButtonAction <T> {

    boolean isApplicable(Message message, Map<Long, ChatState> chatStates);

    void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<T> botApiMethod);
}
