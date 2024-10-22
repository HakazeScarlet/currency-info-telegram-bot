package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.ChatInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.CurrencyHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import net.fellbaum.jemoji.Emojis;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class RetryLastButtonAction implements ButtonAction {

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

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Amount of " +
                    currencyHolder.getCurrent() +
                    " for conversion to " + currencyHolder.getTarget());

                botApiMethod.accept(sendMessage);
                return;
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("You have not had any conversion operations yet. " +
                    "Please perform at least one operation using the \"Convert\" button.");
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
                amount +
                " " + current +
                " " + Emojis.RIGHT_ARROW.getUnicode() +
                " " + target +
                " " + converted
            );

            botApiMethod.accept(sendMessage);
            chatStates.remove(chatId);
            return;
        }
    }
}