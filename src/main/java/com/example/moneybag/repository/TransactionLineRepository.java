package com.example.moneybag.repository;

import com.example.moneybag.classes.Transaction;
import com.example.moneybag.rowmapper.TransactionLineRowMapper;
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
public class TransactionLineRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Add transaction line by providing transaction id, money in and group code
    private void addTransactionLineMoneyIn(){

    }

}
