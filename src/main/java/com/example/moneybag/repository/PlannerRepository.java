package com.example.moneybag.repository;

import com.example.moneybag.classes.PlannerIn;
import com.example.moneybag.classes.PlannerOut;
import com.example.moneybag.rowmapper.PlannerInRowMapper;
import com.example.moneybag.rowmapper.PlannerOutRowMapper;
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

    public List<PlannerOut> getPlannerOutById(int clientId) {
        String sql = "SELECT * FROM planner_out WHERE client_id = (:value) ORDER BY group_code";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.query(sql, plannerMap, new PlannerOutRowMapper());
    }

    public List<PlannerIn> getPlannerInById(int clientId) {
        String sql = "SELECT * FROM planner_in WHERE client_id = (:value) ORDER BY group_code";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.query(sql, plannerMap, new PlannerInRowMapper());
    }



    public void deletePlannerOutByID(int clientId) {
        String sql = "DELETE FROM planner_out WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }

    public void deletePlannerInByID(int clientId) {
        String sql = "DELETE FROM planner_in WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }



    public void insertDefaultPlannerOutById(int clientId, String monthYear, int groupCode, String groupName) {
        String sql =
                "INSERT INTO planner_out (client_id, month_year, group_code, group_name, planned_out, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", monthYear);
        plannerMap.put("value3", groupCode);
        plannerMap.put("value4", groupName);
        plannerMap.put("value5", BigDecimal.ZERO);
        plannerMap.put("value6", BigDecimal.ZERO);
        jdbcTemplate.update(sql, plannerMap);
    }

    public void insertDefaultPlannerInById(int clientId, String monthYear, int groupCode, String groupName) {
        String sql =
                "INSERT INTO planner_in (client_id, month_year, group_code, group_name, planned_in, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", monthYear);
        plannerMap.put("value3", groupCode);
        plannerMap.put("value4", groupName);
        plannerMap.put("value5", BigDecimal.ZERO);
        plannerMap.put("value6", BigDecimal.ZERO);
        jdbcTemplate.update(sql, plannerMap);
    }



    public void insertPlannerOutById(PlannerOut plannerOut) {
        String sql =
                "INSERT INTO planner_out (client_id, month_year, group_code, group_name, planned_out, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", plannerOut.getClientId());
        plannerMap.put("value2", plannerOut.getMonthYear());
        plannerMap.put("value3", plannerOut.getGroupCode());
        plannerMap.put("value4", plannerOut.getGroupName());
        plannerMap.put("value5", plannerOut.getPlannedOut());
        plannerMap.put("value6", plannerOut.getActual());
        jdbcTemplate.update(sql, plannerMap);
    }

    public void insertPlannerInById(PlannerIn plannerIn) {
        String sql =
                "INSERT INTO planner_in (client_id, month_year, group_code, group_name, planned_in, actual) " +
                        "VALUES (:value1, :value2, :value3, :value4, :value5, :value6";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", plannerIn.getClientId());
        plannerMap.put("value2", plannerIn.getMonthYear());
        plannerMap.put("value3", plannerIn.getGroupCode());
        plannerMap.put("value4", plannerIn.getGroupName());
        plannerMap.put("value5", plannerIn.getPlannedIn());
        plannerMap.put("value6", plannerIn.getActual());
        jdbcTemplate.update(sql, plannerMap);
    }



    public BigDecimal getPlannerOutActualById(int clientId, String groupName) {
        String sql = "SELECT actual FROM planner_out WHERE client_id = (:value1) AND group_name = (:value2)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", groupName);
        return jdbcTemplate.queryForObject(sql, plannerMap, BigDecimal.class);
    }

    public BigDecimal getPlannerInActualById(int clientId, String groupName) {
        String sql = "SELECT actual FROM planner_in WHERE client_id = (:value1) AND group_name = (:value2)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", clientId);
        plannerMap.put("value2", groupName);
        return jdbcTemplate.queryForObject(sql, plannerMap, BigDecimal.class);
    }



    public void updatePlannerOutActualById(int clientId, String groupName, BigDecimal amount) {
        String sql = "UPDATE planner_out SET actual = (:value1) WHERE group_name = (:value2) AND client_id = (:value3)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", amount);
        plannerMap.put("value2", groupName);
        plannerMap.put("value3", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }

    public void updatePlannerInActualById(int clientId, String groupName, BigDecimal amount) {
        String sql = "UPDATE planner_in SET actual = (:value1) WHERE group_name = (:value2) AND client_id = (:value3)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value1", amount);
        plannerMap.put("value2", groupName);
        plannerMap.put("value3", clientId);
        jdbcTemplate.update(sql, plannerMap);
    }



    public List<String> getAllOutGroupNamesByClientId(int clientId) {
        String sql = "SELECT group_name FROM planner_out WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.queryForList(sql, plannerMap, String.class);
    }

    public List<String> getAllInGroupNamesByClientId(int clientId) {
        String sql = "SELECT group_name FROM planner_in WHERE client_id = (:value)";
        Map<String, Object> plannerMap = new HashMap<>();
        plannerMap.put("value", clientId);
        return jdbcTemplate.queryForList(sql, plannerMap, String.class);
    }
}
