package com.example.moneybag.classes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class TransactionAddRequest {
    private LocalDate date;
    private String payee;

    private List<TransactionLineAddRequest> transactionLines;

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

    public List<TransactionLineAddRequest> getTransactionLines() {
        return transactionLines;
    }

    public void setTransactionLines(List<TransactionLineAddRequest> transactionLines) {
        this.transactionLines = transactionLines;
    }
}
