package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.ChatInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.CurrencyHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class RetryLastButtonAction implements ButtonAction<SendMessage> {

    private static final String SEPARATOR = "\s";

    private final ChatInfoRepository chatInfoRepository;
    private final CurrencyConverter currencyConverter;

    public RetryLastButtonAction(ChatInfoRepository chatInfoRepository, CurrencyConverter currencyConverter) {
        this.chatInfoRepository = chatInfoRepository;
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
            CurrencyHolder currencyHolder = chatInfoRepository.retrieve(chatId);

            if (currencyHolder != null) {
                ChatState chatState = new ChatState();
                chatState.setAction(ButtonTitle.RETRY_LAST.getTitle());
                chatState.setChatId(chatId);
                chatState.setCurrent(currencyHolder.getCurrent());
                chatState.setTarget(currencyHolder.getTarget());

                chatStates.put(chatId, chatState);

                SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format(
                        "%s %s %s %s",
                        "Amount of", currencyHolder.getCurrent(), "for conversion to", currencyHolder.getTarget()))
                    .build();

                botApiMethod.accept(sendMessage);
                return;
            } else {
                SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(MessagesHolder.FAILED_RETRY_MESSAGE)
                    .build();

                botApiMethod.accept(sendMessage);
                return;
            }
        } else if (chatStates.containsKey(chatId) && chatStates.get(chatId).getAction().equals(ButtonTitle.RETRY_LAST.getTitle())) {
            Double amount = Math.abs(Double.parseDouble(message.getText()));

            ChatState chatState = chatStates.get(chatId);
            String current = chatState.getCurrent();
            String target = chatState.getTarget();
            BigDecimal converted = currencyConverter.convert(current, target, BigDecimal.valueOf(amount));

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(
                amount + SEPARATOR
                + current + SEPARATOR
                + Emojis.RIGHT_ARROW.getUnicode() + SEPARATOR
                + target + SEPARATOR
                + converted
            );

            botApiMethod.accept(sendMessage);
            chatStates.remove(chatId);
            return;
        }
    }
}