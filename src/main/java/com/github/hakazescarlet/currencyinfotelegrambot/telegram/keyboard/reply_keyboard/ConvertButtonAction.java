package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.ChatInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.RetryLastInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.KeyboardBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class ConvertButtonAction implements ButtonAction<SendMessage> {

    private final CurrencyConverter currencyConverter;
    private final RetryLastInfoRepository retryLastInfoRepository;
    private final KeyboardBuilder keyboardBuilder;

    public ConvertButtonAction(
        CurrencyConverter currencyConverter,
        RetryLastInfoRepository retryLastInfoRepository,
        KeyboardBuilder keyboardBuilder
    ) {
        this.currencyConverter = currencyConverter;
        this.retryLastInfoRepository = retryLastInfoRepository;
        this.keyboardBuilder = keyboardBuilder;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        if (!chatStates.isEmpty()) {
            return ButtonTitle.CONVERT.getTitle().equals(chatStates.get(message.getChatId()).getAction());
        }
        return message.getText().contains(ButtonTitle.CONVERT.getTitle());
    }

    @Override
    public void doAction(Message message, Map<Long, ChatState> chatStates, Consumer<SendMessage> botApiMethod) {
        Long chatId = message.getChatId();

        if (!chatStates.containsKey(chatId)) {
            ChatState actionChatState = new ChatState();
            actionChatState.setAction(ButtonTitle.CONVERT.getTitle());
            actionChatState.setChatId(chatId);

            chatStates.put(chatId, actionChatState);

            SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(MessagesHolder.CONVERT_MESSAGE_EXAMPLE)
                .build();
            botApiMethod.accept(sendMessage);

            return;
        }

        ChatState chatState = chatStates.get(chatId);

        // TODO: есть ли смысл в этой проверке (во второй ее части) если есть проверка в методе isApplicable() выше
        if (chatState != null && ButtonTitle.CONVERT.getTitle().equals(chatState.getAction())) {
            String[] currencies = message.getText().split(ConversionMessageHandler.SEPARATOR);
            PairHolder pairHolder = new PairHolder(currencies[0].toUpperCase(), currencies[1].toUpperCase());
            double amount = Math.abs(Double.parseDouble(currencies[2]));

            ConversionInfo conversionInfo = new ConversionInfo();
            conversionInfo.setPairHolder(pairHolder);
            conversionInfo.setAmount(amount);
            chatState.setConversionInfo(conversionInfo);

            BigDecimal converted = currencyConverter.convert(pairHolder, BigDecimal.valueOf(amount));

            SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(ConversionMessageHandler.buildMessage(conversionInfo, converted))
                .build();

            botApiMethod.accept(keyboardBuilder.createInnerFavouriteButton(sendMessage));
            saveRetryLastInfo(chatState);
            chatStates.remove(chatId);
        }
    }

    private void saveRetryLastInfo(ChatState chatState) {
        PairHolder pairHolder = chatState.getConversionInfo().getPairHolder();
        retryLastInfoRepository.save(new ChatInfo(chatState.getChatId(), pairHolder));
    }
}