package com.github.hakazescarlet.currencyinfotelegrambot.telegram.donate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DonateQrCodeHolder {

    private final File donateQrCode;

    public DonateQrCodeHolder(@Value("classpath:images/qr_wallet.jpg") File file) {
        this.donateQrCode = file;
    }

    public File get() {
        return donateQrCode;
    }
}