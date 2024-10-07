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

    private final CurrencyConverter currencyConverter;
    private final ChatInfoRepository chatInfoRepository;

    public ConvertButtonAction(CurrencyConverter currencyConverter, ChatInfoRepository chatInfoRepository) {
        this.currencyConverter = currencyConverter;
        this.chatInfoRepository = chatInfoRepository;
    }

    @Override
    public boolean isApplicable(Message message, Map<Long, ChatState> chatStates) {
        return message.getText().contains(ButtonTitle.CONVERT.getTitle())
            || chatStates.containsKey(message.getChatId());
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
            sendMessage.setText("Convert from");

            botApiMethod.accept(sendMessage);

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

            botApiMethod.accept(sendMessage);

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

            botApiMethod.accept(sendMessage);

            return;
        }

        // TODO: add chat info saving to mongo
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
