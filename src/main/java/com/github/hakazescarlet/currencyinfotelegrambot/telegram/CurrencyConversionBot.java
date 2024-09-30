package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action.ButtonAction;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {

    private final Map<Long, ChatState> chatStates = new HashMap<>();
    private final List<ButtonAction> buttonActions;

    public CurrencyConversionBot(
        @Value("${telegram.bot.token}") String botToken,
        InfoMessageHolder infoMessageHolder,
        List<ButtonAction> buttonActions
    ) {
        super(botToken);
        this.buttonActions = buttonActions;
    }

    // TODO: add validation
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        buttonActions.stream()
            .filter(buttonAction -> buttonAction.isApplicable(message, chatStates))
            .findFirst()
            .orElseThrow(RuntimeException::new)
            .doAction(message, chatStates, (sm) -> {
                try {
                    super.sendApiMethod(sm);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
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