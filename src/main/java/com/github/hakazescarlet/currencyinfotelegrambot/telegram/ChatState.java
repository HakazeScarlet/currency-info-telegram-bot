package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

public class ChatState {

    private Long chatId;
    private String action;

    private ConversionInfo conversionInfo;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ConversionInfo getConversionInfo() {
        return conversionInfo;
    }

    public void setConversionInfo(ConversionInfo conversionInfo) {
        this.conversionInfo = conversionInfo;
    }
}
