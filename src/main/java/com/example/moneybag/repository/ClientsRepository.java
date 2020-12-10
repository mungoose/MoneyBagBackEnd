package com.example.moneybag.repository;

import com.example.moneybag.classes.Client;
import com.example.moneybag.rowmapper.ClientRowMapper;
import com.example.moneybag.service.MoneyBagService;
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
public class ClientsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private MoneyBagService moneyBagService;

    // Add client
    public Integer addClient(String firstName, String lastName, BigDecimal balance, String userName, String password){
        String sql = "INSERT INTO clients (first_name, last_name, balance, username, password) VALUES (:value1, :value2, :value3, :value4, :value5)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value1", firstName);
        clientMap.put("value2", lastName);
        clientMap.put("value3", balance);
        clientMap.put("value4", userName);
        clientMap.put("value5", password);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(clientMap), keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public String askPassword(String userName) {
        String sql = "SELECT password FROM clients WHERE username = (:value)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value", userName);
        return jdbcTemplate.queryForObject(sql, clientMap, String.class);
    }

    // Update balance by id
    public void updateBalanceById(int clientId, BigDecimal balance){
        String sql = "UPDATE clients SET balance = (:value1) WHERE id = (:value2)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value1", balance);
        clientMap.put("value2", clientId);
        jdbcTemplate.update(sql, clientMap);
    }

    // Get client by id
    public Client getClientById(int id){
        String sql = "SELECT * FROM clients WHERE id = (:value)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value", id);
        return jdbcTemplate.queryForObject(sql, clientMap, new ClientRowMapper());
    }

    // Get balance by id
    public BigDecimal getBalanceById(int id){
        String sql = "SELECT balance FROM clients WHERE id = (:value)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value", id);
        return jdbcTemplate.queryForObject(sql, clientMap, BigDecimal.class);
    }

    public Integer getIdByUserName(String userName) {
        String sql = "SELECT id FROM clients WHERE username = (:value)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value", userName);
        return jdbcTemplate.queryForObject(sql, clientMap, Integer.class);
    }
}
