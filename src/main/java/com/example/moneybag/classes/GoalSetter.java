package com.example.moneybag.classes;

import java.math.BigDecimal;

public class GoalSetter {
    private int id;
    private int clientId;
    private int groupCode;
    private BigDecimal maxGoal;

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

    public int getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(int groupCode) {
        this.groupCode = groupCode;
    }

    public BigDecimal getMaxGoal() {
        return maxGoal;
    }

    public void setMaxGoal(BigDecimal maxGoal) {
        this.maxGoal = maxGoal;
    }
}
