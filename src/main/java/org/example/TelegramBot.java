package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {
    private Map<Long, String> userStates = new HashMap<>();
    private Map<Long, Double> carPrices = new HashMap<>();
    private Map<Long, Double> firstPayments = new HashMap<>();
    private Map<Long, Integer> loanTerms = new HashMap<>();
    private Map<Long, String> selectedBanks = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            handleMessage(chatId, message);
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            handleCallback(chatId, callbackData);
        }
    }

    private void handleMessage(long chatId, String message) {
        switch (userStates.getOrDefault(chatId, "")) {
            case "WAITING_FOR_CAR_PRICE":
                handleCarPriceInput(chatId, message);
                break;
            case "WAITING_FOR_FIRST_PAYMENT":
                handleFirstPaymentInput(chatId, message);
                break;
            case "WAITING_FOR_LOAN_TERM":
                handleLoanTermInput(chatId, message);
                break;
            default:
                if (message.equals("/start")) {
                    sendCarOptions(chatId);
                } else {
                    sendMessage(chatId, "Невідома команда. Будь ласка, використовуйте /start для початку.");
                }
                break;
        }
    }

    private void handleCallback(long chatId, String callbackData) {
        switch (callbackData) {
            case "Тойота":
            case "Мазда":
            case "MG":
                userStates.put(chatId, "WAITING_FOR_CAR_PRICE");
                sendMessage(chatId, "Введите стоимость автомобиля:");
                break;
            case "Ощадбанк":
            case "Приватбанк":
            case "Кредит Агриколь":
                selectedBanks.put(chatId, callbackData);
                userStates.put(chatId, "WAITING_FOR_LOAN_TERM");
                sendMessage(chatId, "Введите срок кредита в месяцах:");
                break;
            default:
                sendMessage(chatId, "Невідома команда.");
                break;
        }
    }

    private void handleLoanTermInput(long chatId, String message) {
        try {
            int loanTerm = Integer.parseInt(message);
            loanTerms.put(chatId, loanTerm);

            double carPrice = carPrices.get(chatId);
            double firstPayment = firstPayments.get(chatId);
            String bank = selectedBanks.get(chatId);

            if (bank == null) {
                sendMessage(chatId, "Пожалуйста, выберите банк перед вводом срока кредита.");
                return;
            }

            sendMessage(chatId, String.format("Введенные данные: цена авто = %.2f, первый взнос = %.2f, банк = %s, срок = %d", carPrice, firstPayment, bank, loanTerm));

            double downPaymentPercentage = (firstPayment / carPrice) * 100;
            String loanResults = calculateLoanConditions(carPrice, downPaymentPercentage, bank, loanTerm);
            sendMessage(chatId, loanResults);

            resetUserState(chatId);
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите корректный срок кредита в месяцах.");
        } catch (Exception e) {
            sendMessage(chatId, "Произошла ошибка: " + e.getMessage());
        }
    }

    private void resetUserState(long chatId) {
        userStates.remove(chatId);
        carPrices.remove(chatId);
        firstPayments.remove(chatId);
        loanTerms.remove(chatId);
        selectedBanks.remove(chatId);
    }

    private String calculateLoanConditions(double carPrice, double downPaymentPercentage, String bank, int loanTerm) {
        switch (bank) {
            case "Ощадбанк":
                return Oschad.calculate(carPrice, downPaymentPercentage, loanTerm);
            case "Приватбанк":
                return Privat.calculate(carPrice, downPaymentPercentage, loanTerm);
            case "Кредит Агриколь":
                return KreditAgrikol.calculate(carPrice, downPaymentPercentage, loanTerm);
            default:
                return "Неизвестный банк.";
        }
    }

    private void handleCarPriceInput(long chatId, String message) {
        try {
            double price = Double.parseDouble(message);
            carPrices.put(chatId, price);
            userStates.put(chatId, "WAITING_FOR_FIRST_PAYMENT");
            sendMessage(chatId, "Введите первый взнос:");
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите корректную стоимость автомобиля.");
        }
    }

    private void handleFirstPaymentInput(long chatId, String message) {
        try {
            double firstPayment = Double.parseDouble(message);
            firstPayments.put(chatId, firstPayment);
            userStates.put(chatId, "WAITING_FOR_BANK_SELECTION");
            sendLoanOptions(chatId);
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите корректную сумму первого взноса.");
        }
    }

    private void sendCarOptions(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите марку автомобиля:");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("Тойота").callbackData("Тойота").build());
        row1.add(InlineKeyboardButton.builder().text("Мазда").callbackData("Мазда").build());

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text("MG").callbackData("MG").build());

        buttons.add(row1);
        buttons.add(row2);
        keyboardMarkup.setKeyboard(buttons);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendLoanOptions(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите банк для кредитования:");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("Ощадбанк").callbackData("Ощадбанк").build());
        row1.add(InlineKeyboardButton.builder().text("Приватбанк").callbackData("Приватбанк").build());

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text("Кредит Агриколь").callbackData("Кредит Агриколь").build());

        buttons.add(row1);
        buttons.add(row2);
        keyboardMarkup.setKeyboard(buttons);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "MazdaCredit_Bot"; // Bot username
    }

    @Override
    public String getBotToken() {
        return "7176542474:AAFJbodxYH-70q2zXPFCIs2SsVUZGhFVj8Y"; // Your bot token
    }
}
