package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.FavoriteInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

public class FavoriteButtonAction implements ButtonAction<SendMessage> {

    private final FavoriteInfoRepository favoriteInfoRepository;
    private final CurrencyConverter currencyConverter;

    public FavoriteButtonAction(
        FavoriteInfoRepository favoriteInfoRepository,
        CurrencyConverter currencyConverter
    ) {
        this.favoriteInfoRepository = favoriteInfoRepository;
        this.currencyConverter = currencyConverter;
    }


    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        ChatState chatState = chatStates.get(message.getChatId());
        String buttonTitle = ButtonTitle.FAVORITE.getTitle();
        if (chatState != null) {
            return buttonTitle.equals(chatState.getAction());
        }
        return message.getText().contains(buttonTitle);
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {

    }
}
