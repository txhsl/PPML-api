package org.txhsl.ppml.api.model;

public class UserRequest {
    private String account;
    private String password;
    private double balance;

    public UserRequest() {

    }

    public double getBalance() {
        return balance;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
