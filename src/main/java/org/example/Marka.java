package org.example;

    public class Marka {

    private double price;
    private double downPayment;
    private int loanTerm;
    private double interestRate;
    private double commission;

    public Marka(double price, double downPayment, int loanTerm, double interestRate, double commission) {
        this.price = price;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
        this.commission = commission;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public void validate() throws IllegalArgumentException {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (downPayment < 0 || downPayment > price) {
            throw new IllegalArgumentException("Down payment must be between 0 and the price of the car.");
        }
        if (loanTerm <= 0) {
            throw new IllegalArgumentException("Loan term must be greater than zero.");
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate must be non-negative.");
        }
        if (commission < 0) {
            throw new IllegalArgumentException("Commission must be non-negative.");
        }
    }
}
