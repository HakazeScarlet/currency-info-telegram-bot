package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.inline_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.ChatInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.FavoriteInfoRepository;
import org.springframework.stereotype.Component;

@Component
public class AddToFavouriteButtonAction {

    private final FavoriteInfoRepository favoriteInfoRepository;

    public AddToFavouriteButtonAction(FavoriteInfoRepository favoriteInfoRepository) {
        this.favoriteInfoRepository = favoriteInfoRepository;
    }

    public void saveToDb(ChatInfo chatInfo) {
        favoriteInfoRepository.save(chatInfo);
    }
}
