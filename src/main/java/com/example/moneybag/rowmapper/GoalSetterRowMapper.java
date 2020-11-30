package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.GoalSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalSetterRowMapper implements RowMapper<GoalSetter> {

    @Override
    public GoalSetter mapRow(ResultSet resultSet, int i) throws SQLException {
        GoalSetter goalSetter = new GoalSetter();
        goalSetter.setId(resultSet.getInt("id"));
        goalSetter.setClientId(resultSet.getInt("client_id"));
        goalSetter.setGroupCode(resultSet.getInt("group_code"));
        goalSetter.setMaxGoal(resultSet.getBigDecimal("max_goal"));
        return goalSetter;
    }
}
