package com.example.moneybag.repository;

import com.example.moneybag.classes.Transaction;
import com.example.moneybag.classes.TransactionAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Create initial blank transaction line
    public void addTransactionInitial(int id) {

    }

    // Add transaction money in, set money_out to zero
/*
    public Integer addTransactionMoneyIn(Transaction transaction, BigDecimal newBalance, int id) {
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";

        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", transaction.getDate());
        transactionMap.put("value3", transaction.getPayee());
        transactionMap.put("value4", transaction.getMoneyIn());
        transactionMap.put("value5", BigDecimal.ZERO);
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", false);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(transactionMap), keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }
*/
    // Create initial transaction line
    public Integer createInitialTransaction(int id){
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";

        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", LocalDateTime.now());
        transactionMap.put("value3", "init");
        transactionMap.put("value4", BigDecimal.ZERO);
        transactionMap.put("value5", BigDecimal.ZERO);
        transactionMap.put("value6", BigDecimal.ZERO);
        transactionMap.put("value7", false);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(transactionMap), keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    // Update transaction money in, set money_out to zero
    public Integer addTransactionMoneyIn2
    (LocalDate date, String payee, BigDecimal newBalance, int clientId, BigDecimal transactionSum, int id) {
        String sql = "UPDATE transactions SET (client_id, date, payee, money_in, money_out, end_balance, locked) = (:value1, :value2, :value3, :value4, :value5, :value6, :value7) " +
                "WHERE id = (:value8) ";

        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", clientId);
        transactionMap.put("value2", date);
        transactionMap.put("value3", payee);
        transactionMap.put("value4", transactionSum);
        transactionMap.put("value5", BigDecimal.ZERO);
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", false);
        transactionMap.put("value8", id);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(transactionMap), keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }


    // Add transaction money out, set money_in to zero
    public Integer addTransactionMoneyOut(Transaction transaction, BigDecimal newBalance, int id) {
        String sql = "INSERT INTO transactions (client_id, date, payee, money_in, money_out, end_balance, locked) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";

        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value1", id);
        transactionMap.put("value2", transaction.getDate());
        transactionMap.put("value3", transaction.getPayee());
        transactionMap.put("value4", BigDecimal.ZERO);
        transactionMap.put("value5", transaction.getMoneyOut());
        transactionMap.put("value6", newBalance);
        transactionMap.put("value7", false);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(transactionMap), keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

}
