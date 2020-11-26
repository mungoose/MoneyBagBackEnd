package com.example.moneybag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionLineRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Add transaction line by providing transaction id, money in and group code
    public void addTransactionLine(int transactionId, BigDecimal moneyIn, BigDecimal moneyOut, int groupCode){
        String sql = "INSERT INTO transaction_lines (transaction_id, money_in, money_out, group_code)" +
                "VALUES (:value1, :value2, :value3, :value4)";

        Map<String, Object> transactionLineRow = new HashMap<>();
        transactionLineRow.put("value1", transactionId);
        transactionLineRow.put("value2", moneyIn);
        transactionLineRow.put("value3", moneyOut);
        transactionLineRow.put("value4", groupCode);

        jdbcTemplate.update(sql, transactionLineRow);

    }

}
