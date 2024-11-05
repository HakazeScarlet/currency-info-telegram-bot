package com.github.hakazescarlet.currencyinfotelegrambot.telegram.donate;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action.ButtonAction;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class DonateButtonAction implements ButtonAction {

    private final DonateQrCodeHolder donateQrCodeHolder;
    private final String walletLink = "TNbd9sLFerZfyH9P7FfoUsAkD6f5HTTUqj";

    public DonateButtonAction(DonateQrCodeHolder donateQrCodeHolder) {
        this.donateQrCodeHolder = donateQrCodeHolder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.DONATE.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        Long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("You can help our project by donating to the specified link to the crypto wallet on the TRC-20 network"
            + "\n" + walletLink
            + "\n" + "or use QR-code :) ");
        botApiMethod.accept(sendMessage);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(donateQrCodeHolder.get()));

    }

//    @Override
//    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
//        SendInvoice.builder()
//            .chatId(message.getChatId())
//            .currency("RUB")
//            .providerToken("")
//            .title("Support us")
//            .description("This project is completely free. Your help is completely voluntary :)")
//            .payload("Donate")
//            .price(new LabeledPrice("Donate", 100))
//            .startParameter("Test")
//            .build();
//    }
}
