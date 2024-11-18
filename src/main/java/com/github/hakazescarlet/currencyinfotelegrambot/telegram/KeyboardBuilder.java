package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardBuilder {

    // TODO: refactor this part
    public SendMessage createReplyKeyboard(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(Emojis.CURRENCY_EXCHANGE.getUnicode() + ButtonTitle.CONVERT.getTitle()));
        keyboardFirstRow.add(new KeyboardButton(Emojis.STAR.getUnicode() + ButtonTitle.FAVORITE.getTitle()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(Emojis.RECYCLING_SYMBOL_UNQUALIFIED.getUnicode()
            + ButtonTitle.RETRY_LAST.getTitle()));
        keyboardSecondRow.add(new KeyboardButton(Emojis.CLOCKWISE_VERTICAL_ARROWS.getUnicode()
            + ButtonTitle.SWAP_CURRENT.getTitle()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(Emojis.MONEY_WITH_WINGS.getUnicode() + ButtonTitle.DONATE.getTitle()));
        keyboardThirdRow.add(new KeyboardButton(Emojis.CLIPBOARD.getUnicode() + ButtonTitle.HELP.getTitle()));
        keyboardThirdRow.add(new KeyboardButton(Emojis.MEMO.getUnicode() + ButtonTitle.FEEDBACK.getTitle()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(new KeyboardButton(Emojis.PROHIBITED.getUnicode() + ButtonTitle.CANCEL.getTitle()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage createInnerFavouriteButton(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        InlineKeyboardButton favouriteButton = new InlineKeyboardButton();
        favouriteButton.setText(Emojis.STAR.getUnicode() + ButtonTitle.ADD_TO_FAVOURITE.getTitle());
        favouriteButton.setCallbackData(ButtonTitle.ADD_TO_FAVOURITE.getTitle());

        firstRow.add(favouriteButton);
        keyboard.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}