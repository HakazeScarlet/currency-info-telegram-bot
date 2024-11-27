package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.PairHolder;

public class ConversionInfo {

    private PairHolder pairHolder;
    private Double amount;

    public PairHolder getPairHolder() {
        return pairHolder;
    }

    public void setPairHolder(PairHolder pairHolder) {
        this.pairHolder = pairHolder;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
