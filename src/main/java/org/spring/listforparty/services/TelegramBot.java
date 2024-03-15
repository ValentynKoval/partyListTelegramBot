package org.spring.listforparty.services;

import lombok.RequiredArgsConstructor;
import org.spring.listforparty.configs.BotConfig;
import org.spring.listforparty.dto.CustomerDto;
import org.spring.listforparty.dto.OfferDto;
import org.spring.listforparty.models.Customer;
import org.spring.listforparty.models.Offer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final CustomerService customerService;
    private final OfferService offerService;
    private final ProductService productService;

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, "@" + update.getMessage().getChat().getUserName());
                    break;
                case "/showWishes":
                    showWishes(chatId);
                    break;
                case "/showProductsList":
                    sendMessage(chatId, "--------СПИСОК--------\n" + productService.findAll());
                    break;
                default:
                    if (messageText.toLowerCase().startsWith("хочу")) {
                        messageText = messageText.substring(5);

                        if (customerService.findByChatId(chatId).isPresent()) {
                            Offer offer = offerService.saveNewOffer(new OfferDto(messageText));
                            customerService.addNewOffer(chatId, offer);
                        } else {
                            Offer offer = offerService.saveNewOffer(new OfferDto(messageText));
                            CustomerDto customerDto = new CustomerDto();
                            customerDto.setUsername(update.getMessage().getChat().getUserName());
                            customerDto.setFirstName(update.getMessage().getChat().getFirstName());
                            customerDto.setLastName(update.getMessage().getChat().getLastName());
                            customerDto.setChatId(update.getMessage().getChat().getId());
                            customerDto.setOffer(offer);
                            customerService.saveNewCustomer(customerDto);
                        }
                        sendMessage(chatId, "Ваше пожелание было добавлено");
                    } else if (messageText.toLowerCase().startsWith("список")) {
                        productService.saveNewProducts(messageText);
                        sendMessage(chatId, "Список был сохранен");
                    } else {
                        sendMessage(chatId, "Команда не поддерживаеться");
                    }
                    break;
            }
        }
    }

    public void showWishes(long chatId) {
        List<Customer> customers = customerService.findAll();
        String textToSend = "";
        for (Customer customer : customers) {
            textToSend += customer.toString() + "\n\n";
        }
        sendMessage(chatId, textToSend);
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", напиши что ты хочешь добавить в список на дом в виде: \"Хочу ...\"";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
