package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class DonateButtonAction implements ButtonAction<SendPhoto> {

    private final File donateQrCode;

    public DonateButtonAction() {
        try {
            URL resource = getClass().getResource("/images/qr_wallet.jpg");
            this.donateQrCode = new File(resource.toURI());
            BufferedImage bufferedImage = ImageIO.read(resource);
            ImageIO.write(bufferedImage, "jpg", donateQrCode);
        } catch (Exception e) {
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

        SendPhoto sendPhoto = SendPhoto.builder()
            .chatId(chatId)
            .caption(MessagesHolder.MESSAGE_CAPTION)
            .photo(new InputFile(donateQrCode))
            .build();

        botApiMethod.accept(sendPhoto);
    }
}
