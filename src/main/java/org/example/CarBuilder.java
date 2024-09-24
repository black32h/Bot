package org.example;

public class CarBuilder {
    private String carModel; // Добавлено поле для модели автомобиля
    private double price;
    private double downPayment;
    private int loanTerm;
    private double interestRate;
    private double commission;

    public CarBuilder setCarModel(String carModel) {
        this.carModel = carModel;
        return this;
    }

    public CarBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public CarBuilder setDownPayment(double downPayment) {
        this.downPayment = downPayment;
        return this;
    }

    public CarBuilder setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
        return this;
    }

    public CarBuilder setInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public CarBuilder setCommission(double commission) {
        this.commission = commission;
        return this;
    }

    public Car build() {
        return new Car(carModel, price, downPayment, loanTerm, interestRate, commission);
    }
}
