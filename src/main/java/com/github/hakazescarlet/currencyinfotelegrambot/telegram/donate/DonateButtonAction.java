package com.github.hakazescarlet.currencyinfotelegrambot.telegram.donate;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action.ButtonAction;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class DonateButtonAction implements ButtonAction {
    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.DONATE.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        SendInvoice.builder()
            .chatId(message.getChatId())
            .currency("RUB")
            .providerToken("")
            .title("Support us")
            .description("This project is completely free. Your help is completely voluntary :)")
            .payload("Donate")
            .price(new LabeledPrice("Donate", 100))
            .startParameter("Test")
            .build();
    }
}
