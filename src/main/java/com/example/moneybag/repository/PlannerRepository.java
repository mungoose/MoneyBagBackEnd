package com.example.moneybag.repository;

import com.example.moneybag.classes.Planner;
import com.example.moneybag.rowmapper.PlannerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlannerRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Planner> getPlannerById(int id) {
        String sql = "SELECT * FROM planner WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", id);
        return jdbcTemplate.query(sql, plannerMap, new PlannerRowMapper());
    }
}
