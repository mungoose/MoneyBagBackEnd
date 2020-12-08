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
        transactionLine.setMoneyInLine(resultSet.getBigDecimal("money_in_line"));
        transactionLine.setMoneyOutLine(resultSet.getBigDecimal("money_out_line"));
        transactionLine.setGroupCode(resultSet.getInt("group_code"));
        transactionLine.setGroupName(resultSet.getString("group_name"));
        return transactionLine;
    }
}
