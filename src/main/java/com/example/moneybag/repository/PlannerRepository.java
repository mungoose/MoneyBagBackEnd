package com.example.moneybag.repository;

import com.example.moneybag.classes.Planner;
import com.example.moneybag.rowmapper.PlannerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlannerRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Planner> getPlannerById(int clientId) {
        String sql = "SELECT * FROM planner WHERE client_id = (:value) ORDER BY id";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.query(sql, plannerMap, new PlannerRowMapper());
    }

    public void deletePlannerByID(int clientId) {
        String sql = "DELETE FROM planner WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }

    public void insertPlannerById(Planner planner) {
        String sql =
                "INSERT INTO planner (client_id, month_year, group_code, group_name, planned_in, planned_out, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", planner.getClientId());
        plannerMap.put("value2", planner.getMonthYear());
        plannerMap.put("value3", planner.getGroupCode());
        plannerMap.put("value4", planner.getGroupName());
        plannerMap.put("value5", planner.getPlannedIn());
        plannerMap.put("value6", planner.getPlannedOut());
        plannerMap.put("value7", planner.getActual());
        jdbcTemplate.update(sql, plannerMap);
    }

    public void insertDefaultPlannerById(int clientId, String monthYear, int groupCode, String groupName) {
        String sql =
                "INSERT INTO planner (client_id, month_year, group_code, group_name, planned_in, planned_out, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6, :value7)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", monthYear);
        plannerMap.put("value3", groupCode);
        plannerMap.put("value4", groupName);
        plannerMap.put("value5", BigDecimal.ZERO);
        plannerMap.put("value6", BigDecimal.ZERO);
        plannerMap.put("value7", BigDecimal.ZERO);
        jdbcTemplate.update(sql, plannerMap);
    }

    public BigDecimal getPlannerActualById(int clientId, String groupName) {
        String sql = "SELECT actual FROM planner WHERE client_id = (:value1) AND group_name = (:value2)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", groupName);
        return jdbcTemplate.queryForObject(sql, plannerMap, BigDecimal.class);
    }

    public void updatePlannerActualById(int clientId, String groupName, BigDecimal amount) {
        String sql = "UPDATE planner SET actual = (:value1) WHERE group_name = (:value2) AND client_id = (:value3)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", amount);
        plannerMap.put("value2", groupName);
        plannerMap.put("value3", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }

    // Get all client group names by client id
    public List<String> getAllGroupNamesByClientId(int clientId) {
        String sql = "SELECT group_name FROM planner WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.queryForList(sql, plannerMap, String.class);
    }
}
