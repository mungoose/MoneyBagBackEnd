package com.example.moneybag.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionCombined {
    private int id;
    private int clientId;
    private LocalDate date;
    private String payee;
    private BigDecimal moneyIn;
    private BigDecimal moneyOut;
    private BigDecimal endBalance;
    private boolean locked;
    private List<TransactionLinesList> transactionLinesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(BigDecimal moneyIn) {
        this.moneyIn = moneyIn;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(BigDecimal moneyOut) {
        this.moneyOut = moneyOut;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public List<TransactionLinesList> getTransactionLinesList() {
        return transactionLinesList;
    }

    public void setTransactionLinesList(List<TransactionLinesList> transactionLinesList) {
        this.transactionLinesList = transactionLinesList;
    }

}
