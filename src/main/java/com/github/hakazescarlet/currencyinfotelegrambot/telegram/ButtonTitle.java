package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

public enum ButtonTitle {

    CONVERT ("Convert"),
    FAVORITE ("Favorite"),
    RETRY_LAST ("Retry last"),
    SWAP_CURRENT ("Swap Current"),
    DONATE ("Donate"),
    HELP ("Help"),
    FEEDBACK ("Feedback"),
    CANCEL ("Cancel"),
    ADD_TO_FAVOURITE ("Add to Favourite");

    private final String title;

    ButtonTitle(String buttonsTitle) {
        this.title = buttonsTitle;
    }

    public String getTitle() {
        return title;
    }
}
