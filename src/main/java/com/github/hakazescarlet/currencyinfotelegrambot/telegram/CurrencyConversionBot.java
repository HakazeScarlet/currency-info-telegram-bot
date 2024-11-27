package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.inline_keyboard.AddToFavouriteButtonAction;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard.ButtonAction;
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
    private final List<ButtonAction<?>> buttonActions;
    private final AddToFavouriteButtonAction addToFavouriteButtonAction;

    public CurrencyConversionBot(
        @Value("${telegram.bot.token}") String botToken,
        List<ButtonAction<?>> buttonActions,
        AddToFavouriteButtonAction addToFavouriteButtonAction
    ) {
        super(botToken);
        this.buttonActions = buttonActions;
        this.addToFavouriteButtonAction = addToFavouriteButtonAction;
    }

    // TODO: add validation
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            Message message = (Message) update.getCallbackQuery().getMessage();

//            PairHolder currencyPair = ConversionMessagesBuilder.parsePair(message.getText());

//            String current = favoriteCurrencies[1].toUpperCase();
//            String target = favoriteCurrencies[4].toUpperCase();

//            addToFavouriteButtonAction.saveToDb(new ChatInfo(message.getChatId(), currencyPair.current(), currencyPair.target()));
        } else {
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

    }

    @Override
    public String getBotUsername() {
        return "Scarlet_Currency_Converter_Bot";
    }

    private static final class TwoAndMoreButtonActionMatchesException extends RuntimeException {

        public TwoAndMoreButtonActionMatchesException() {
            super();
        }
    }

    private static final class NoneButtonActionMatchesException extends RuntimeException {

        public NoneButtonActionMatchesException() {
            super();
        }
    }
}