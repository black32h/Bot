package org.example;

public class KreditAgrikol {
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        double downPayment = carPrice * downPaymentPercentage / 100; // Преобразуем процент в сумму
        double loanAmount = carPrice - downPayment;

        // Проверка, чтобы сумма кредита не была отрицательной
        if (loanAmount <= 0) {
            return "Первый взнос не может быть больше или равен цене автомобиля.";
        }

        double interestRate = 13.51 / 100; // Годовая процентная ставка
        double monthlyRate = interestRate / 12;
        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));

        // Расходы
        double kascoAnnual = 0.06 * carPrice; // КАСКО 6% от цены в год
        double lifeInsurance = 21373; // Страхование жизни
        double registrationCost = 65250; // Стоимость первой регистрации
        double notaryServices = 5000; // Услуги нотариуса

        double totalKasco = kascoAnnual * (loanTerm / 12.0); // Общее КАСКО за весь срок кредита
        double monthlyKasco = kascoAnnual / 12; // Ежемесячный платеж по КАСКО
        double totalPayment = (monthlyPayment * loanTerm) + totalKasco + lifeInsurance + registrationCost + notaryServices;

        return String.format("Кредит Агриколь:\n" +
                        "Сумма кредита: %.2f грн\n" +
                        "Ежемесячный платеж: %.2f грн\n" +
                        "Ежемесячный платеж по КАСКО: %.2f грн\n" +
                        "Годовая стоимость КАСКО: %.2f грн\n" +
                        "Общая стоимость КАСКО за весь срок: %.2f грн\n" +
                        "Страхование жизни: %.2f грн\n" +
                        "Стоимость первой регистрации: %.2f грн\n" +
                        "Услуги нотариуса: %.2f грн\n" +
                        "Разовая комиссия: 0.00 грн\n" +
                        "Общая сумма выплат: %.2f грн\n" +
                        "Примечание: КАСКО составляет 6%% от стоимости автомобиля.\n",
                loanAmount, monthlyPayment, monthlyKasco, kascoAnnual, totalKasco, lifeInsurance, registrationCost, notaryServices, totalPayment);
    }
}
