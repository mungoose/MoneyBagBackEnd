package com.example.moneybag.classes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionAddRequest {
    private LocalDateTime date;
    private String payee;
    private List<TransactionLineAddRequest> transactionLines;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
