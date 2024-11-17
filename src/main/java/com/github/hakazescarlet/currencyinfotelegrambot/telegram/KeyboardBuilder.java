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
        keyboardFirstRow.add(new KeyboardButton(ButtonTitle.CONVERT.getTitle()
            + Emojis.CURRENCY_EXCHANGE.getUnicode()));
        keyboardFirstRow.add(new KeyboardButton(ButtonTitle.FAVORITE.getTitle()
            + Emojis.STAR.getUnicode()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(ButtonTitle.RETRY_LAST.getTitle()
            + Emojis.RECYCLING_SYMBOL_UNQUALIFIED.getUnicode()));
        keyboardSecondRow.add(new KeyboardButton(ButtonTitle.SWAP_CURRENT.getTitle()
            + Emojis.CLOCKWISE_VERTICAL_ARROWS.getUnicode()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(ButtonTitle.DONATE.getTitle()
            + Emojis.MONEY_WITH_WINGS.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton(ButtonTitle.HELP.getTitle()
            + Emojis.CLIPBOARD.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton(ButtonTitle.FEEDBACK.getTitle()
            + Emojis.MEMO.getUnicode()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(new KeyboardButton(ButtonTitle.CANCEL.getTitle()
            + Emojis.PROHIBITED.getUnicode()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage createInnerFavouriteButton(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        InlineKeyboardButton favouriteButton = new InlineKeyboardButton();
        favouriteButton.setText(Emojis.STAR.getUnicode() + " Add to Favorite");
        favouriteButton.setCallbackData(ButtonTitle.FAVORITE.getTitle());

        firstRow.add(favouriteButton);
        keyboard.add(firstRow);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
