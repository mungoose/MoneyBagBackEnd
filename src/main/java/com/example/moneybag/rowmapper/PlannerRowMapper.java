package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.Planner;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannerRowMapper implements RowMapper<Planner> {

    @Override
    public Planner mapRow(ResultSet resultSet, int i) throws SQLException {
        Planner planner = new Planner();
        planner.setId(resultSet.getInt("id"));
        planner.setClientId(resultSet.getInt("client_id"));
        planner.setMonthYear(resultSet.getString("month_year"));
        planner.setGroupCode(resultSet.getInt("group_code"));
        planner.setGroupName(resultSet.getString("group_name"));
        planner.setPlanned(resultSet.getBigDecimal("planned"));
        planner.setActual(resultSet.getBigDecimal("actual"));
        return planner;
    }
}
