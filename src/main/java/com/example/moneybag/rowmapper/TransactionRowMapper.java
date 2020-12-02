package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException{
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt("id"));
        transaction.setClientId(resultSet.getInt("client_id"));
        transaction.setDate(resultSet.getDate("date").toLocalDate());
        transaction.setPayee(resultSet.getString("payee"));
        transaction.setMoneyIn(resultSet.getBigDecimal("money_in"));
        transaction.setMoneyOut(resultSet.getBigDecimal("money_out"));
        transaction.setEndBalance(resultSet.getBigDecimal("end_balance"));
        transaction.setLocked(resultSet.getBoolean("locked"));
        return transaction;
    }
}


