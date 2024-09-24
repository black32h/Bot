package org.example;

public class Privat {
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        double downPayment = carPrice * downPaymentPercentage / 100; // Преобразуем процент в сумму
        double loanAmount = carPrice - downPayment;

        // Проверка, чтобы сумма кредита не была отрицательной
        if (loanAmount <= 0) {
            return "Первый взнос не может быть больше или равен цене автомобиля.";
        }

        double interestRateFirstPeriod = getInterestRateFirstPeriod(downPaymentPercentage);
        double interestRateAfterFirstPeriod = getInterestRateAfterFirstPeriod(downPaymentPercentage);

        // Логика расчета для первого периода
        int firstPeriod = Math.min(loanTerm, 24);
        double monthlyPaymentFirstPeriod = calculateMonthlyPayment(loanAmount, interestRateFirstPeriod, firstPeriod);
        double totalPaymentFirstPeriod = monthlyPaymentFirstPeriod * firstPeriod;

        double remainingLoanAmount = loanAmount; // Остаток кредита после первого периода
        int remainingTerm = loanTerm - firstPeriod;

        // Логика расчета для оставшегося периода
        double monthlyPaymentAfterFirstPeriod = 0;
        if (remainingTerm > 0) {
            monthlyPaymentAfterFirstPeriod = calculateMonthlyPayment(remainingLoanAmount, interestRateAfterFirstPeriod, remainingTerm);
        }

        // Расходы
        double kascoAnnual = 0.0699 * carPrice; // КАСКО 6.99% от цены в год
        double totalKasco = kascoAnnual * (loanTerm / 12.0); // Общее КАСКО за весь срок кредита
        double monthlyKasco = kascoAnnual / 12; // Ежемесячный платеж по КАСКО
        double commission = 0; // Разовая комиссия 0%

        double totalPayment = totalPaymentFirstPeriod + (monthlyPaymentAfterFirstPeriod * Math.max(remainingTerm, 0)) + totalKasco + commission;

        return String.format("Приватбанк:\n" +
                        "Сумма кредита: %.2f грн\n" +
                        "Ежемесячный платеж: %.2f грн (первые %d месяцев)\n" +
                        "Ежемесячный платеж после %d месяцев: %.2f грн\n" +
                        "Ежемесячный платеж по КАСКО: %.2f грн\n" +
                        "Годовая стоимость КАСКО: %.2f грн\n" +
                        "Общая стоимость КАСКО за весь срок: %.2f грн\n" +
                        "Разовая комиссия: %.2f грн\n" +
                        "Общая сумма выплат: %.2f грн\n" +
                        "Примечание: КАСКО составляет 6.99%% от стоимости автомобиля.\n",
                loanAmount, monthlyPaymentFirstPeriod, firstPeriod, firstPeriod, monthlyPaymentAfterFirstPeriod,
                monthlyKasco, kascoAnnual, totalKasco, commission, totalPayment);
    }

    private static double getInterestRateFirstPeriod(double downPaymentPercentage) {
        if (downPaymentPercentage == 20) return 6.5;
        if (downPaymentPercentage == 30) return 4.9;
        if (downPaymentPercentage == 40) return 2.9;
        if (downPaymentPercentage == 50) return 0.01;
        if (downPaymentPercentage == 60) return 0.01;
        return 0; // Если процент не подходит
    }

    private static double getInterestRateAfterFirstPeriod(double downPaymentPercentage) {
        return 21.9; // Это фиксированная ставка
    }

    private static double calculateMonthlyPayment(double loanAmount, double interestRate, int months) {
        if (months <= 0) return 0; // Проверка на количество месяцев
        double monthlyRate = interestRate / 100 / 12;
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }
}
