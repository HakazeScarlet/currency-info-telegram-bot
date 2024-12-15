package com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.reply_keyboard;

import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.ChatInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.RetryLastInfoRepository;
import com.github.hakazescarlet.currencyinfotelegrambot.chat_bot_storage.entities.PairHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.currency_conversion.CurrencyConverter;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ButtonTitle;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ChatState;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.ConversionInfo;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.MessagesHolder;
import com.github.hakazescarlet.currencyinfotelegrambot.telegram.keyboard.KeyboardBuilder;
import net.fellbaum.jemoji.Emojis;
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
            try {
                String[] currencies = message.getText().split(ConversionMessageHandler.SEPARATOR);
                String[] validCurrencies = ConvertButtonAction.validateConvertMessage(currencies);
                PairHolder pairHolder = new PairHolder(validCurrencies[0].toUpperCase(), validCurrencies[1].toUpperCase());
                // TODO: добавить валидацию для currencies[2]
                Double amount = Double.parseDouble(validCurrencies[2]);

                ConversionInfo conversionInfo = new ConversionInfo();
                conversionInfo.setPairHolder(pairHolder);
                conversionInfo.setAmount(amount);
                chatState.setConversionInfo(conversionInfo);
            } catch (ConvertMessageValidationException e) {
                SendMessage sendMessage = keyboardBuilder.createReplyKeyboard(message.getChatId());
                sendMessage.setText("Check your currencies and try again. Example "
                    + Emojis.RIGHT_ARROW.getUnicode()
                    + " USD EUR 1000"
                );
            }
            try {
                BigDecimal converted = currencyConverter.convert(pairHolder, amount);

                SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(ConversionMessageHandler.buildMessage(conversionInfo, converted))
                    .build();

                botApiMethod.accept(keyboardBuilder.createInnerFavouriteButton(sendMessage));
                saveRetryLastInfo(chatState);
                chatStates.remove(chatId);
            } catch (CurrencyConverter.CurrencyCodeException e) {
                SendMessage sendMessage = keyboardBuilder.createReplyKeyboard(message.getChatId());
                sendMessage.setText("Can't convert "
                    + pairHolder.getCurrent() + " & " + pairHolder.getTarget()
                    + ". Check your currencies and try again. Example "
                    + Emojis.RIGHT_ARROW.getUnicode()
                    + " USD EUR 1000"
                );

                chatStates.remove(chatId);
                botApiMethod.accept(sendMessage);
            } catch (CurrencyConverter.NoPairCurrencyException e) {
                SendMessage sendMessage = keyboardBuilder.createReplyKeyboard(message.getChatId());
                sendMessage.setText("Incorrect message format. Example "
                    + Emojis.RIGHT_ARROW.getUnicode()
                    + " USD EUR 1000"
                );

                chatStates.remove(chatId);
                botApiMethod.accept(sendMessage);
            }
        }
    }

    private void saveRetryLastInfo(ChatState chatState) {
        PairHolder pairHolder = chatState.getConversionInfo().getPairHolder();
        retryLastInfoRepository.save(new ChatInfo(chatState.getChatId(), pairHolder));
    }

    private static String[] validateConvertMessage(String[] currencies) {
        if (currencies.length != 3) {
            return currencies;
        }
        throw new ConvertMessageValidationException("Uncorrected convert message");
    }

    public static final class ConvertMessageValidationException extends RuntimeException {

        public ConvertMessageValidationException(String message) {
            super(message);
        }

    }
}