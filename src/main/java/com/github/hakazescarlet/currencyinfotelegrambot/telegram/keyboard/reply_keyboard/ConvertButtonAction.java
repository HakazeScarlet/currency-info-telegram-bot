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

    private static final String SEPARATOR = "\s";

    private final CurrencyConverter currencyConverter;
    private final RetryLastInfoRepository retryLastInfoRepository;
    private final KeyboardBuilder keyboardBuilder;
    private final ConversionMessageHandler conversionMessageHandler;

    public ConvertButtonAction(
        CurrencyConverter currencyConverter,
        RetryLastInfoRepository retryLastInfoRepository,
        KeyboardBuilder keyboardBuilder,
        ConversionMessageHandler conversionMessageHandler) {
        this.currencyConverter = currencyConverter;
        this.retryLastInfoRepository = retryLastInfoRepository;
        this.keyboardBuilder = keyboardBuilder;
        this.conversionMessageHandler = conversionMessageHandler;
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
            String[] currencies = message.getText().split(SEPARATOR);

            String current = currencies[0].toUpperCase();
            String target = currencies[1].toUpperCase();
            double amount = Math.abs(Double.parseDouble(currencies[2]));

            ConversionInfo conversionInfo = new ConversionInfo();
            conversionInfo.setPairHolder(new PairHolder(current, target));
            conversionInfo.setAmount(amount);

            chatState.setConversionInfo(conversionInfo);

            BigDecimal converted = currencyConverter.convert(current, target, BigDecimal.valueOf(amount));

            SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(conversionMessageHandler.buildMessage(conversionInfo, converted))
                .build();

            botApiMethod.accept(keyboardBuilder.createInnerFavouriteButton(sendMessage));
            saveChatInfo(chatState);
            chatStates.remove(chatId);
        }
    }

    private void saveChatInfo(ChatState chatState) {
        PairHolder pairHolder = chatState.getConversionInfo().getPairHolder();
        retryLastInfoRepository.save(new ChatInfo(chatState.getChatId(), pairHolder.getCurrent(), pairHolder.getTarget())
        );
    }
}
