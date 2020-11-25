package com.example.moneybag.rowmapper;

import com.example.moneybag.classes.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException{
        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setClientId(resultSet.getInt("client_id"));
        group.setGroupCode(resultSet.getInt("group_code"));
        group.setGroupName(resultSet.getString("group_name"));
        return group;
    }
}
