package org.example;

import java.util.HashMap;
import java.util.Map;

public class CarLoanCalculator {

    public static void main(String[] args) {
        // Параметры кредита
        double carPrice = 1000000;
        double downPaymentPercentage = 0.30; // 30% первоначального взноса
        double downPayment = carPrice * downPaymentPercentage;
        double loanAmount = carPrice - downPayment;

        double[] rates = {3.49, 6.99, 8.99, 11.99, 11.99}; // массив процентных ставок
        int[] terms = {12, 24, 36, 48, 60}; // массив сроков кредита

        for (int i = 0; i < rates.length; i++) { // вызов методов для расчета и проверки кредитных условий
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299; // добавляем комиссию
                System.out.printf("Кредит на %d месяцев под %.2f%% годовых (включая одноразовую комиссию): платеж %.2f\n",
                        terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
                System.out.println("Одноразовая комиссия составляет 2,99% от суммы кредита");
            } else {
                System.out.printf("Кредит на %d месяцев под %.2f%% годовых (без одноразовой комиссии): платеж %.2f\n",
                        terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
            }
        }

        // Создаем экземпляр LoanChecker (не забудьте реализовать этот класс)
        LoanChecker loanChecker = new LoanChecker();
        loanChecker.checkLoanOptions(loanAmount, downPayment, rates, terms, 10000);
    }

    // Метод для расчета ежемесячного платежа
    public static double calculateMonthlyPayment(double loanAmount, double annualRate, int months) {
        double monthlyRate = annualRate / 100 / 12;
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    // Метод для расчета условий кредита
    public Map<Integer, Double> calculateLoanTerms(double carPrice, double downPaymentPercentage) {
        double downPayment = carPrice * (downPaymentPercentage / 100);
        double loanAmount = carPrice - downPayment;

        Map<Integer, Double> results = new HashMap<>();
        double[] rates = {3.49, 6.99, 8.99, 11.99, 11.99}; // массив процентных ставок
        int[] terms = {12, 24, 36, 48, 60}; // массив сроков кредита

        for (int i = 0; i < rates.length; i++) {
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299; // добавляем комиссию
            }
            double monthlyPayment = calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
            results.put(terms[i], monthlyPayment); // сохраняем результат
        }

        return results;
    }
}
