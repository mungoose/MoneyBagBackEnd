package com.example.moneybag.classes;

import java.math.BigDecimal;

public class TransactionLine {

    private int id;
    private int transactionId;
    private BigDecimal moneyInLine;
    private BigDecimal moneyOutLine;
    private int groupCode;
    private String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getMoneyInLine() {
        return moneyInLine;
    }

    public void setMoneyInLine(BigDecimal moneyInLine) {
        this.moneyInLine = moneyInLine;
    }

    public BigDecimal getMoneyOutLine() {
        return moneyOutLine;
    }

    public void setMoneyOutLine(BigDecimal moneyOutLine) {
        this.moneyOutLine = moneyOutLine;
    }

    public int getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(int groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
