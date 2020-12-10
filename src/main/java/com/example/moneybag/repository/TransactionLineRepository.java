package com.example.moneybag.repository;

import com.example.moneybag.classes.TransactionLine;
import com.example.moneybag.rowmapper.TransactionLineRowMapper;
import liquibase.pro.packaged.M;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionLineRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Add transaction line by providing transaction id, money in and group code
    public void addTransactionLine
    (int transactionId, BigDecimal moneyInLine, BigDecimal moneyOutLine, int groupCode, String groupName) {
        String sql = "INSERT INTO transaction_lines " +
                "(transaction_id, money_in_line, money_out_line, group_code, group_name) " +
                "VALUES (:value1, :value2, :value3, :value4, :value5)";

        Map<String, Object> transactionLineRow = new HashMap<>();
        transactionLineRow.put("value1", transactionId);
        transactionLineRow.put("value2", moneyInLine);
        transactionLineRow.put("value3", moneyOutLine);
        transactionLineRow.put("value4", groupCode);
        transactionLineRow.put("value5", groupName);
        jdbcTemplate.update(sql, transactionLineRow);
    }

    // Get transaction lines by client id
    public List<TransactionLine> getTransactionLinesById(int clientId) {
        String sql = "SELECT * FROM transaction_lines WHERE client_id = (:value)";
        Map <String, Object> transactionLineMap = new HashMap<>();
        transactionLineMap.put("value", clientId);
        return jdbcTemplate.query(sql, transactionLineMap, new TransactionLineRowMapper());
    }

    // Get transaction lines by client id and transaction id
    public List<TransactionLine> getTransactionLinesByTransactionId(int transactionId) {
        String sql = "SELECT * FROM transaction_lines WHERE transaction_id = (:value)";
        Map <String, Object> transactionLineMap = new HashMap<>();
        transactionLineMap.put("value", transactionId);
        return jdbcTemplate.query(sql, transactionLineMap, new TransactionLineRowMapper());
    }


/*    // Get transactions and transaction lines combined by client id
    public List<TransactionCombined> getTransactionsCombined(int clientId) {
        String sql = "SELECT * FROM transactions e JOIN transaction_lines v ON e.id = v.transaction_id " +
                "WHERE client_id = (:value)";
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("value", clientId);
        return jdbcTemplate.query(sql, transactionMap, new TransactionCombinedRowMapper());
    }*/



    //One group all money going in
    public List<BigDecimal> getGroupSumIN(int groupCode, int clientid) {
        String sql = "SELECT money_in_line FROM transaction_lines WHERE (group_code, client_id) = " + "(:value, :value1)";
        Map<String, Object> moneyinMap = new HashMap<>();
        moneyinMap.put("value", groupCode);
        moneyinMap.put("value1", clientid);
        return jdbcTemplate.queryForList(sql, moneyinMap, BigDecimal.class);
    }

    //One group all money going out
    public List<BigDecimal> getGroupSumOUT(int groupCode, int clientid) {
        String sql = "SELECT money_out_line FROM transaction_lines WHERE (group_code, client_id) = " + "(:value, :value1)";
        Map<String, Object> moneyoutMap = new HashMap<>();
        moneyoutMap.put("value", groupCode);
        moneyoutMap.put("value1", clientid);
        return jdbcTemplate.queryForList(sql, moneyoutMap, BigDecimal.class);
    }

}
