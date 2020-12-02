package com.example.moneybag.classes;

import java.math.BigDecimal;

public class Planner {

    private int id;
    private int clientId;
    private String monthYear;
    private int groupCode;
    private String groupName;
    private BigDecimal plannedIn;
    private BigDecimal plannedOut;
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

    public BigDecimal getPlannedIn() {
        return plannedIn;
    }

    public void setPlannedIn(BigDecimal plannedIn) {
        this.plannedIn = plannedIn;
    }

    public BigDecimal getPlannedOut() {
        return plannedOut;
    }

    public void setPlannedOut(BigDecimal plannedOut) {
        this.plannedOut = plannedOut;
    }

    public BigDecimal getActual() {
        return actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }
}
