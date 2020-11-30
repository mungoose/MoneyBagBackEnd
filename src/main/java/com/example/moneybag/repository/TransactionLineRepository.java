package com.example.moneybag.repository;

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
    public void addTransactionLine(int transactionId, BigDecimal moneyIn, BigDecimal moneyOut, int groupCode, int clientid){
        String sql = "INSERT INTO transaction_lines (transaction_id, money_in, money_out, group_code, client_id)" +
                "VALUES (:value1, :value2, :value3, :value4, :value5)";

        Map<String, Object> transactionLineRow = new HashMap<>();
        transactionLineRow.put("value1", transactionId);
        transactionLineRow.put("value2", moneyIn);
        transactionLineRow.put("value3", moneyOut);
        transactionLineRow.put("value4", groupCode);
        transactionLineRow.put("value5", clientid);
        jdbcTemplate.update(sql, transactionLineRow);

    }

    //One group all money going in
    public List<BigDecimal> getGroupSumIN (int groupCode, int clientid) {
        String sql = "SELECT money_in FROM transaction_lines WHERE (group_code, client_id) = " + "(:value, :value1)";
        Map<String, Object> moneyinMap= new HashMap<>();
        moneyinMap.put("value", groupCode);
        moneyinMap.put("value1", clientid);
        return jdbcTemplate.queryForList(sql, moneyinMap, BigDecimal.class);
    }

    //One group all money going out
    public List<BigDecimal> getGroupSumOUT (int groupCode, int clientid) {
        String sql = "SELECT money_out FROM transaction_lines WHERE (group_code, client_id) = " + "(:value, :value1)";
        Map<String, Object> moneyoutMap= new HashMap<>();
        moneyoutMap.put("value", groupCode);
        moneyoutMap.put("value1", clientid);
        return jdbcTemplate.queryForList(sql, moneyoutMap, BigDecimal.class);
    }

}
