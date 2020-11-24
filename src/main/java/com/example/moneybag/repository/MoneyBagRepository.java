package com.example.moneybag.repository;

import com.example.moneybag.classes.Test2;
import com.example.moneybag.rowmapper.NameRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MoneyBagRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Test2> getname(int i){
        String sql = "SELECT * FROM test2";
        Map nameMap = new HashMap<>();
        return jdbcTemplate.query(sql, nameMap, new NameRowMapper());
    }
}
