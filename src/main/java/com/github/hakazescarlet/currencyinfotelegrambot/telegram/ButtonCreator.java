package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonCreator {

    // TODO: refactor this part
    // TODO: add history & fast convert buttons
    public SendMessage create(long chatId) {
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
}
