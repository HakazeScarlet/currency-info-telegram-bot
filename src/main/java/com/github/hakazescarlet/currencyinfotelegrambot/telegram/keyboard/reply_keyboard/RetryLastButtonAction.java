package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.RetryLastInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class RetryLastButtonAction implements ButtonAction<SendMessage> {

    private final RetryLastInfoRepository retryLastInfoRepository;
    private final CurrencyConverter currencyConverter;

    public RetryLastButtonAction(
        RetryLastInfoRepository retryLastInfoRepository,
        CurrencyConverter currencyConverter
    ) {
        this.retryLastInfoRepository = retryLastInfoRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        ChatState chatState = chatStates.get(message.getChatId());
        if (chatState != null) {
            return ButtonTitle.RETRY_LAST.getTitle().equals(chatState.getAction());
        }
        return message.getText().contains(ButtonTitle.RETRY_LAST.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        Long chatId = message.getChatId();

        if (!chatStates.containsKey(chatId)) {
            PairHolder pairHolder = retryLastInfoRepository.retrieve(chatId);

            if (pairHolder != null) {
                ChatState chatState = new ChatState();

                chatState.setAction(ButtonTitle.RETRY_LAST.getTitle());
                chatState.setChatId(chatId);
                ConversionInfo conversionInfo = new ConversionInfo();
                conversionInfo.setPairHolder(pairHolder);
                chatState.setConversionInfo(conversionInfo);

                chatStates.put(chatId, chatState);

                SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format(
                        "%s %s %s %s",
                        "Amount of", pairHolder.getCurrent(), "for conversion to", pairHolder.getTarget()))
                    .build();

                botApiMethod.accept(sendMessage);
            } else {
                SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(MessagesHolder.FAILED_RETRY_MESSAGE)
                    .build();

                botApiMethod.accept(sendMessage);
            }
        } else if (chatStates.containsKey(chatId) && chatStates.get(chatId).getAction().equals(ButtonTitle.RETRY_LAST.getTitle())) {
            Double amount = Math.abs(Double.parseDouble(message.getText()));

            ChatState chatState = chatStates.get(chatId);
            PairHolder pairHolder = chatState.getConversionInfo().getPairHolder();
            BigDecimal converted = currencyConverter.convert(pairHolder, BigDecimal.valueOf(amount));

            ConversionInfo conversionInfo = new ConversionInfo();
            conversionInfo.setPairHolder(pairHolder);
            conversionInfo.setAmount(amount);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(ConversionMessageHandler.buildMessage(conversionInfo, converted));

            botApiMethod.accept(sendMessage);
            chatStates.remove(chatId);
        }
    }
}