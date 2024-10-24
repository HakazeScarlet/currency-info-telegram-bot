package com.github.hakazescarlet.currencyinfotelegrambot.telegram.button_action;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.ChatInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_info_storage.ChatInfoRepository;
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
public class ConvertButtonAction implements ButtonAction {

    private static final String SEPARATOR = "\s";

    private final CurrencyConverter currencyConverter;
    private final ChatInfoRepository chatInfoRepository;

    public ConvertButtonAction(CurrencyConverter currencyConverter, ChatInfoRepository chatInfoRepository) {
        this.currencyConverter = currencyConverter;
        this.chatInfoRepository = chatInfoRepository;
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

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(
                "Add currencies to conversion and amount "
                + "\nExample "
                + Emojis.RIGHT_ARROW.getUnicode()
                + " USD EUR 1000");

            botApiMethod.accept(sendMessage);

            return;
        }

        ChatState chatState = chatStates.get(chatId);

        if (chatState != null && ButtonTitle.CONVERT.getTitle().equals(chatState.getAction())) {
            String convertMessage = message.getText();
            String[] currencies = convertMessage.split(SEPARATOR);

            chatState.setCurrent(currencies[0].toUpperCase());
            chatState.setTarget(currencies[1].toUpperCase());
            chatState.setAmount(Math.abs(Double.parseDouble(currencies[2])));

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            String current = chatState.getCurrent();
            String target = chatState.getTarget();
            Double amount = chatState.getAmount();
            BigDecimal converted = currencyConverter.convert(current, target, BigDecimal.valueOf(amount));
            sendMessage.setText(
                amount + SEPARATOR
                + current + SEPARATOR
                + Emojis.RIGHT_ARROW.getUnicode() + SEPARATOR
                + converted + SEPARATOR
                + target);

            botApiMethod.accept(sendMessage);
            saveChatInfo(chatState);
            chatStates.remove(chatId);

            return;
        }
    }

    private void saveChatInfo(ChatState chatState) {
        ChatInfo chatInfo = new ChatInfo(chatState.getChatId(), chatState.getCurrent(), chatState.getTarget());
        chatInfoRepository.save(chatInfo);
    }
}
