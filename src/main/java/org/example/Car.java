package org.example;

public class Car {
    private String model;
    private double price;
    private double downPayment;
    private int loanTerm;
    private double interestRate;
    private double commission;

    public Car(String model, double price, double downPayment, int loanTerm, double interestRate, double commission) {
        this.model = model;
        this.price = price;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
        this.commission = commission;
    }

    // Getters and setters...
}
