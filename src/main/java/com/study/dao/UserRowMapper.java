package com.study.dao;

import com.study.entity.Product;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper {

    public List<User> getRow(ResultSet resultSet) throws SQLException {
        List<User> list = new ArrayList<>();
        while (resultSet.next()){
            User user = new User(resultSet.getString("username"),
                    resultSet.getString("password"),//hash should be added
                    UserRole.valueOf(resultSet.getString("role"))
                    );
            user.setSole(resultSet.getString("sole"));
            user.setPassword(resultSet.getString("hashedPassword"));
            list.add(user);
        }
        return list;
    }
}
