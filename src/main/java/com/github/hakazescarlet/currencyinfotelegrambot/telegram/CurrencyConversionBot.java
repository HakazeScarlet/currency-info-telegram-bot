package com.github.hakazescarlet.currencyinfotelegrambot.telegram;

import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import lombok.SneakyThrows;
import net.fellbaum.jemoji.Emojis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyConversionBot extends TelegramLongPollingBot {

    private final Map<Long, ChatState> chatStates = new HashMap<>();
    private final ButtonCreator buttonCreator;
    private final InfoMessageHolder infoMessageHolder;
    private final CurrencyConverter currencyConverter;

    public CurrencyConversionBot(
        @Value("${telegram.bot.token}") String botToken,
        ButtonCreator buttonCreator,
        InfoMessageHolder infoMessageHolder,
        CurrencyConverter currencyConverter
    ) {
        super(botToken);
        this.buttonCreator = buttonCreator;
        this.infoMessageHolder = infoMessageHolder;
        this.currencyConverter = currencyConverter;
    }

    // TODO: add validation
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = update.getMessage().getChatId();
        if (message.getText().equals("/start")) {
            SendMessage sendMessage = buttonCreator.create(chatId);
            sendMessage.setText(infoMessageHolder.get());
            sendApiMethod(sendMessage);
        } else {
            if (message.getText().contains(ButtonTitle.CONVERT.getTitle())
                || chatStates.containsKey(chatId)) {

                if (!chatStates.containsKey(chatId)) {
                    ChatState actionChatState = new ChatState();
                    actionChatState.setAction(ButtonTitle.CONVERT.getTitle());
                    actionChatState.setChatId(chatId);

                    chatStates.put(chatId, actionChatState);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Convert from");
                    sendApiMethod(sendMessage);
                    return;
                }

                ChatState chatState = chatStates.get(chatId);

                if (chatState != null && chatState.getAction().contains(ButtonTitle.CONVERT.getTitle())
                    && chatState.getCurrent() == null) {

                    chatState.setCurrent(message.getText().toUpperCase());
                    chatStates.put(chatId, chatState);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Convert to");
                    sendApiMethod(sendMessage);
                    return;
                }

                if (chatState != null && chatState.getAction().contains(ButtonTitle.CONVERT.getTitle())
                    && chatState.getCurrent() != null
                    && chatState.getTarget() == null) {

                    chatState.setTarget(message.getText().toUpperCase());
                    chatStates.put(chatId, chatState);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Amount of " + chatState.getCurrent());
                    sendApiMethod(sendMessage);
                    return;
                }

                if (chatState != null && chatState.getAction().contains(ButtonTitle.CONVERT.getTitle())
                    && chatState.getCurrent() != null
                    && chatState.getTarget() != null
                    && chatState.getAmount() == null) {

                    chatState.setAmount(Math.abs(Double.parseDouble(message.getText())));
                    chatStates.put(chatId, chatState);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(chatState.getAmount()
                        + " " + chatState.getCurrent()
                        + " " + Emojis.RIGHT_ARROW.getUnicode()
                        + " " + chatState.getTarget()
                        + " " + currencyConverter.convert(
                        chatState.getCurrent(),
                        chatState.getTarget(),
                        BigDecimal.valueOf(chatState.getAmount())).toString());
                    sendApiMethod(sendMessage);

                    chatStates.remove(chatId);
                    return;
                }

            } else if (message.getText().contains(ButtonTitle.HELP.getTitle())) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(infoMessageHolder.get());
                sendApiMethod(sendMessage);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Scarlet_Currency_Converter_Bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}