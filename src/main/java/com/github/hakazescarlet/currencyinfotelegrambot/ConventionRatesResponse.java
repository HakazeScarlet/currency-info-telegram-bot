package com.github.hakazescarlet.currencyinfotelegrambot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConventionRatesResponse {

    private String currencyTitle;
    private double conventionRate;

    public String getCurrencyTitle() {
        return currencyTitle;
    }

    public void setCurrencyTitle(String currencyTitle) {
        this.currencyTitle = currencyTitle;
    }

    public double getConventionRate() {
        return conventionRate;
    }

    public void setConventionRate(double conventionRate) {
        this.conventionRate = conventionRate;
    }
}
