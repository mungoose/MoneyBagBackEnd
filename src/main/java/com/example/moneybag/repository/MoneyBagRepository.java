package com.example.moneybag.repository;

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



}
