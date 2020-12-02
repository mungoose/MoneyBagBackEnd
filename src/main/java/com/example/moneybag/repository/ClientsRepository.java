package com.example.moneybag.repository;

import com.example.moneybag.classes.Client;
import com.example.moneybag.rowmapper.ClientRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClientsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Add client
    public void addClient(Client client){
        String sql = "INSERT INTO clients (first_name, last_name, balance) VALUES (:value1, :value2, :value3)";
        Map<String, Object> clientMap = new HashMap<>();
        clientMap.put("value1", client.getFirstName());
        clientMap.put("value2", client.getLastName());
        clientMap.put("value3", BigDecimal.ZERO);
        jdbcTemplate.update(sql, clientMap);
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

}
