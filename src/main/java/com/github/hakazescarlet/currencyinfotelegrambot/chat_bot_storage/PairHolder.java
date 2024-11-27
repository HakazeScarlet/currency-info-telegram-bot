package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage;

import java.util.Objects;

public class PairHolder {

    private final String current;
    private final String target;

    public PairHolder(String current, String target) {
        this.current = current;
        this.target = target;
    }

    public String getCurrent() {
        return current;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PairHolder that = (PairHolder) o;
        return Objects.equals(current, that.current) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, target);
    }
}