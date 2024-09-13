package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class ButtonCreator {

    public SendMessage createButtons(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(ButtonsTitle.CONVERT.getButtonsTitle()
            + Emojis.CURRENCY_EXCHANGE.getUnicode()));
        keyboardFirstRow.add(new KeyboardButton(ButtonsTitle.FAVORITE.getButtonsTitle()
            + Emojis.STAR.getUnicode()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(ButtonsTitle.RETRY_LAST.getButtonsTitle()
            + Emojis.RECYCLING_SYMBOL_UNQUALIFIED.getUnicode()));
        keyboardSecondRow.add(new KeyboardButton(ButtonsTitle.SWAP_CURRENT.getButtonsTitle()
            + Emojis.CLOCKWISE_VERTICAL_ARROWS.getUnicode()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(ButtonsTitle.DONATE.getButtonsTitle()
            + Emojis.MONEY_WITH_WINGS.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton(ButtonsTitle.HELP.getButtonsTitle()
            + Emojis.CLIPBOARD.getUnicode()));
        keyboardThirdRow.add(new KeyboardButton(ButtonsTitle.FEEDBACK.getButtonsTitle()
            + Emojis.MEMO.getUnicode()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(new KeyboardButton(ButtonsTitle.CANCEL.getButtonsTitle()
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
