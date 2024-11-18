package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.FavoriteInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class AddToFavouriteButtonAction implements ButtonAction<SendMessage> {

    private final FavoriteInfoRepository favoriteInfoRepository;

    public AddToFavouriteButtonAction(FavoriteInfoRepository favoriteInfoRepository) {
        this.favoriteInfoRepository = favoriteInfoRepository;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return false;
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {

    }
}
