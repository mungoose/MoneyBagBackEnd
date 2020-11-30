package com.example.moneybag.repository;


import com.example.moneybag.classes.GoalSetter;
import com.example.moneybag.classes.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GoalSetterRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Create goal by providing client id, code nr and name for the group
    public void addGoal (GoalSetter goalSetter){
        String sql = "INSERT INTO goal_setter (client_id, group_code, max_goal) VALUES (:value1, :value2, :value3)";
        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("value1", goalSetter.getClientId());
        groupMap.put("value2", goalSetter.getGroupCode());
        groupMap.put("value3", goalSetter.getMaxGoal());
        jdbcTemplate.update(sql, groupMap);
    }

    // Change' by providing client id, code nr and name for the group
    public void changeGoal (GoalSetter goalSetter){
        String sql = "UPDATE goal_setter SET max_goal = (:value3) WHERE (client_id, group_code)="+"(:value1, :value2)";
        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("value1", goalSetter.getClientId());
        groupMap.put("value2", goalSetter.getGroupCode());
        groupMap.put("value3", goalSetter.getMaxGoal());
        jdbcTemplate.update(sql, groupMap);
    }
}
