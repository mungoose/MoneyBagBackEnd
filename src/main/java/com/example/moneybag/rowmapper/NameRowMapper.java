package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.Test2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NameRowMapper implements RowMapper<Test2> {

    @Override
    public Test2 mapRow(ResultSet resultSet, int i) throws SQLException{
        Test2 test2 = new Test2();
        test2.setId(resultSet.getInt("id"));
        test2.setFirstName(resultSet.getString("first_name"));
        test2.setLastName(resultSet.getString("last_name"));
        return test2;
    }
}
