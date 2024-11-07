package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class DonateButtonAction implements ButtonAction<SendPhoto> {

    private final File donateQrCode;
    private static final String WALLET_LINK = "TNbd9sLFerZfyH9P7FfoUsAkD6f5HTTUqj";

    public DonateButtonAction() {
        try {
            donateQrCode = ImageIO.read(getClass().getResource("images/qr_wallet.jpg"));
//            URI uri = getClass().getResource("/images/donate.png").toURI();
//            donateQrCode =
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.DONATE.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendPhoto> botApiMethod) {
        Long chatId = message.getChatId();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption("You can help our project by donating to the specified link to the crypto wallet on the TRC-20 network"
            + "\n" + WALLET_LINK
            + "\n" + "or use QR-code :) ");
        sendPhoto.setPhoto(new InputFile(donateQrCode));
        botApiMethod.accept(sendPhoto);
    }
}
