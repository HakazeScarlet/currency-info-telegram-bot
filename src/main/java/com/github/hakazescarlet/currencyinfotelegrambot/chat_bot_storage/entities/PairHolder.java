package com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities;

import java.util.Objects;

public class PairHolder {

    private String current;
    private String target;

    public PairHolder() {
    }

    public PairHolder(String current, String target) {
        this.current = current;
        this.target = target;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairHolder that = (PairHolder) o;

        if (!Objects.equals(current, that.current)) return false;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        int result = current != null ? current.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }
}
