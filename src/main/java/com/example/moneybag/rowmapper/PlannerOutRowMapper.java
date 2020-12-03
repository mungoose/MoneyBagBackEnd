package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.PlannerOut;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannerOutRowMapper implements RowMapper<PlannerOut> {

    @Override
    public PlannerOut mapRow(ResultSet resultSet, int i) throws SQLException {
        PlannerOut plannerOut = new PlannerOut();
        plannerOut.setId(resultSet.getInt("id"));
        plannerOut.setClientId(resultSet.getInt("client_id"));
        plannerOut.setMonthYear(resultSet.getString("month_year"));
        plannerOut.setGroupCode(resultSet.getInt("group_code"));
        plannerOut.setGroupName(resultSet.getString("group_name"));
        plannerOut.setPlannedOut(resultSet.getBigDecimal("planned_out"));
        plannerOut.setActual(resultSet.getBigDecimal("actual"));
        return plannerOut;
    }
}
