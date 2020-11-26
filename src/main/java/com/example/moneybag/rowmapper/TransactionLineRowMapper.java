package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.TransactionLine;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionLineRowMapper implements RowMapper<TransactionLine> {

    @Override
    public TransactionLine mapRow(ResultSet resultSet, int i) throws SQLException{
        TransactionLine transactionLine = new TransactionLine();
        transactionLine.setId(resultSet.getInt("id"));
        transactionLine.setTransactionId(resultSet.getInt("transaction_id"));
        transactionLine.setMoneyIn(resultSet.getBigDecimal("money_in"));
        transactionLine.setMoneyOut(resultSet.getBigDecimal("money_out"));
        transactionLine.setGroupCode(resultSet.getInt("group_code"));
        return transactionLine;
    }
}
