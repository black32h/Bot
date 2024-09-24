package org.example;

public class Oschad {
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        double downPayment = carPrice * downPaymentPercentage / 100; // Преобразуем процент в сумму
        double loanAmount = carPrice - downPayment;

        // Проверка, чтобы сумма кредита не была отрицательной
        if (loanAmount <= 0) {
            return "Первый взнос не может быть больше или равен цене автомобиля.";
        }

        double interestRate = getInterestRate(loanTerm, downPaymentPercentage); // Годовая процентная ставка
        double monthlyRate = interestRate / 100 / 12;
        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));

        // Расходы
        double kascoAnnual = 0.07 * carPrice; // КАСКО 7% от цены в год
        double commissionRate = getCommissionRate(loanTerm); // Разовая комиссия
        double commission = loanAmount * commissionRate;

        double registrationCost = 65250; // Стоимость первой регистрации
        double loanIssuanceFeeRate = 0.015; // Комиссия за выдачу кредита 1.5%
        double loanIssuanceFee = loanAmount * loanIssuanceFeeRate;

        double totalKasco = kascoAnnual * (loanTerm / 12.0); // Общее КАСКО за весь срок кредита
        double monthlyKasco = kascoAnnual / 12; // Ежемесячный платеж по КАСКО
        double totalPayment = (monthlyPayment * loanTerm) + totalKasco + commission + registrationCost + loanIssuanceFee;

        return String.format("Ощадбанк:\n" +
                        "Сумма кредита: %.2f грн\n" +
                        "Ежемесячный платеж: %.2f грн\n" +
                        "Ежемесячный платеж по КАСКО: %.2f грн\n" +
                        "Годовая стоимость КАСКО: %.2f грн\n" +
                        "Общая стоимость КАСКО за весь срок: %.2f грн\n" +
                        "Разовая комиссия: %.2f грн\n" +
                        "Стоимость первой регистрации: %.2f грн\n" +
                        "Комиссия за выдачу кредита (1.5%% от суммы кредита): %.2f грн\n" +
                        "Общая сумма выплат: %.2f грн\n" +
                        "Примечание: КАСКО составляет 7%% от стоимости автомобиля.\n",
                loanAmount, monthlyPayment, monthlyKasco, kascoAnnual, totalKasco, commission, registrationCost, loanIssuanceFee, totalPayment);
    }

    private static double getCommissionRate(int loanTerm) {
        if (loanTerm <= 12) {
            return 0.0199;
        } else if (loanTerm <= 24) {
            return 0.035;
        } else if (loanTerm <= 36) {
            return 0.035;
        } else if (loanTerm <= 60) {
            return 0.0;
        } else if (loanTerm <= 84) {
            return 0.0;
        }
        return 0; // Неподходящий срок
    }

    private static double getInterestRate(int loanTerm, double downPaymentPercentage) {
        if (loanTerm <= 12) {
            return getRateForTerm12Months(downPaymentPercentage);
        } else if (loanTerm <= 24) {
            return getRateForTerm24Months(downPaymentPercentage);
        } else if (loanTerm <= 36) {
            return getRateForTerm36Months(downPaymentPercentage);
        } else if (loanTerm <= 60) {
            return getRateForTerm60Months(downPaymentPercentage);
        } else if (loanTerm <= 84) {
            return getRateForTerm84Months(downPaymentPercentage);
        }
        return 0; // Неподходящий срок
    }

    private static double getRateForTerm12Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 6.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 4.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 2.99;
        } else if (downPaymentPercentage >= 50) {
            return 0.01;
        }
        return 0; // Неподходящий процент
    }

    private static double getRateForTerm24Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 10.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 8.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 7.99;
        } else if (downPaymentPercentage >= 50) {
            return 3.99;
        }
        return 0; // Неподходящий процент
    }

    private static double getRateForTerm36Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 11.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 9.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 8.99;
        } else if (downPaymentPercentage >= 50) {
            return 7.99;
        }
        return 0; // Неподходящий процент
    }

    private static double getRateForTerm60Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 14.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 12.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 12.99;
        } else if (downPaymentPercentage >= 50) {
            return 11.99;
        }
        return 0; // Неподходящий процент
    }

    private static double getRateForTerm84Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 15.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 14.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 12.99;
        } else if (downPaymentPercentage >= 50) {
            return 12.99;
        }
        return 0; // Неподходящий процент
    }
}
