package com.example.moneybag.repository;

import com.example.moneybag.classes.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GroupsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Create group by providing client id, code nr and name for the group
    public void addGroup(Group group){
        String sql = "INSERT INTO groups (client_id, group_code, group_name) VALUES (:value1, :value2, :value3)";
        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("value1", group.getClientId());
        groupMap.put("value2", group.getGroupCode());
        groupMap.put("value3", group.getGroupName());
        jdbcTemplate.update(sql, groupMap);
    }

    // Get group name by group code
    public String getGroupNameByCode(int code){
        String sql = "SELECT group_name FROM groups WHERE group_code = (:value)";
        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("value", code);
        return jdbcTemplate.queryForObject(sql, groupMap, String.class);
    }


}
