package com.example.moneybag.classes;

import java.math.BigDecimal;

public class Planner {

    private int id;
    private int clientId;
    private String monthYear;
    private int groupCode;
    private String groupName;
    private BigDecimal planned;
    private BigDecimal actual;

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

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
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

    public BigDecimal getPlanned() {
        return planned;
    }

    public void setPlanned(BigDecimal planned) {
        this.planned = planned;
    }

    public BigDecimal getActual() {
        return actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }

}
