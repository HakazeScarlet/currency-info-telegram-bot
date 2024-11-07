package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action.ButtonAction;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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
            .reduce((a, b) -> {
                throw new TwoAndMoreButtonActionMatchesException();
            })
            .orElseThrow(() -> new NoneButtonActionMatchesException())
            .doAction(message, chatStates, (sendMessage) -> {

                try {
                    if (sendMessage instanceof SendMessage) {
                        super.sendApiMethod((SendMessage) sendMessage);
                    } else if (sendMessage instanceof SendPhoto) {
                        super.execute((SendPhoto) sendMessage);
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @Override
    public String getBotUsername() {
        return "Scarlet_Currency_Converter_Bot";
    }

    private final class TwoAndMoreButtonActionMatchesException extends RuntimeException {

        public TwoAndMoreButtonActionMatchesException() {
            super();
        }
    }

    private final class NoneButtonActionMatchesException extends RuntimeException {

        public NoneButtonActionMatchesException() {
            super();
        }
    }
}