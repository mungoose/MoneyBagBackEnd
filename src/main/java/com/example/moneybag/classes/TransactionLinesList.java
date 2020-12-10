package com.example.moneybag.classes;

import java.math.BigDecimal;

public class TransactionLinesList {

    private int id;
    private BigDecimal moneyInLine;
    private BigDecimal moneyOutLine;
    private String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
