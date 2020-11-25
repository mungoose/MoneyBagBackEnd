package com.example.moneybag.repository;

import com.example.moneybag.classes.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Add transaction money in, set money_out to zero
    public void addTransactionMoneyIn(Transaction transaction, BigDecimal newBalance, int id) {
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", transaction.getDate());
        transactionMap.put("value3", transaction.getPayee());
        transactionMap.put("value4", transaction.getMoneyIn());
        transactionMap.put("value5", BigDecimal.ZERO);
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", transaction.getLocked());
        jdbcTemplate.update(sql, transactionMap);
    }

    // Add transaction money out, set money_in to zero
    public void addTransactionMoneyOut(Transaction transaction, BigDecimal newBalance, int id) {
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", transaction.getDate());
        transactionMap.put("value3", transaction.getPayee());
        transactionMap.put("value4", BigDecimal.ZERO);
        transactionMap.put("value5", transaction.getMoneyOut());
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", transaction.getLocked());
        jdbcTemplate.update(sql, transactionMap);
    }

    // Add transaction money out, set money_in to zero
    public Integer addTransactionMoneyOutKeyHolder(Transaction transaction, BigDecimal newBalance, int id) {
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", transaction.getDate());
        transactionMap.put("value3", transaction.getPayee());
        transactionMap.put("value4", BigDecimal.ZERO);
        transactionMap.put("value5", transaction.getMoneyOut());
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", transaction.getLocked());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(transactionMap), keyHolder);
//        jdbcTemplate.update(sql, transactionMap);

        return (Integer) keyHolder.getKeys().get("id");
    }

}
