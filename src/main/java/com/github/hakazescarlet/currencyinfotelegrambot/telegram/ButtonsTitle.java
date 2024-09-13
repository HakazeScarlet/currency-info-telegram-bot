package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

public enum ButtonsTitle {

    CONVERT ("Convert "),
    FAVORITE ("Favorite "),
    RETRY_LAST ("Retry last "),
    SWAP_CURRENT ("Swap Current "),
    DONATE ("Donate "),
    HELP ("Help "),
    FEEDBACK ("Feedback "),
    CANCEL ("Cancel ");

    private String buttonsTitle;

    ButtonsTitle(String buttonsTitle) {
        this.buttonsTitle = buttonsTitle;
    }

    public String getButtonsTitle() {
        return buttonsTitle;
    }
}
