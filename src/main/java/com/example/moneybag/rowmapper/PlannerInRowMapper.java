package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.PlannerIn;
import com.example.moneybag.classes.PlannerOut;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannerInRowMapper implements RowMapper<PlannerIn> {

    @Override
    public PlannerIn mapRow(ResultSet resultSet, int i) throws SQLException {
        PlannerIn plannerIn = new PlannerIn();
        plannerIn.setId(resultSet.getInt("id"));
        plannerIn.setClientId(resultSet.getInt("client_id"));
        plannerIn.setMonthYear(resultSet.getString("month_year"));
        plannerIn.setGroupCode(resultSet.getInt("group_code"));
        plannerIn.setGroupName(resultSet.getString("group_name"));
        plannerIn.setPlannedIn(resultSet.getBigDecimal("planned_in"));
        plannerIn.setActual(resultSet.getBigDecimal("actual"));
        return plannerIn;
    }
}
