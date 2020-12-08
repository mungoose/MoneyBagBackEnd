package com.example.moneybag.classes;

import java.math.BigDecimal;

public class TransactionLineAddRequest {

    private BigDecimal moneyInLine;
    private BigDecimal moneyOutLine;
    private int groupCode;
    private String groupName;

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
