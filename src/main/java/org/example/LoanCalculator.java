package org.example;
public class LoanCalculator {
    public static void main(String[] args) {
        double carPrice = 30000; // Ціна автомобіля
        double[] downPayments = {0.20, 0.30, 0.40, 0.50, 0.60}; // Відсотки авансового внеску
        double[] interestRates = {21.9, 21.9, 21.9, 21.9, 14.9}; // Відсотки після 2 років
        int loanTerm = 60; // Строк кредитування в місяцях

        for (int i = 0; i < downPayments.length; i++) {
            double downPayment = carPrice * downPayments[i];
            double loanAmount = carPrice - downPayment;
            double interestRate = interestRates[i] / 100;
            double monthlyRate = interestRate / 12;
            double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));

            System.out.printf("Авансовий внесок: %.0f%%, Щомісячний платіж: %.2f грн\n", downPayments[i] * 100, monthlyPayment);
        }
    }
}
