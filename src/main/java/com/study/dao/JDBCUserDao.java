package com.study.dao;

import com.study.security.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {

    private static final String USER_UPD_QUERY = "UPDATE shop.users set hashedPassword = ?, sole = ?, role = ? WHERE \"user\" = ? ";
    private static final String GET_USER_BY_LOGIN = "SELECT \"user\" username,password,role,sole, hashedPassword FROM shop.users t WHERE t.user = ? ;";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;

    public JDBCUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void update(User user) {
        try (Connection connnection = dataSource.getConnection();
             PreparedStatement stmt = connnection.prepareStatement(USER_UPD_QUERY);) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getSole());
            stmt.setString(3, user.getUserRole().toString());
            stmt.setString(4, user.getLogin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public boolean isExisting(String user, String password) {
        return getOne(user) != null;
    }

    @Override
    public User getOne(String user) {
        List<User> list = new ArrayList<>();
            try (Connection connnection = dataSource.getConnection();
                 PreparedStatement stmt = connnection.prepareStatement(GET_USER_BY_LOGIN);) {
                stmt.setString(1, user);
                stmt.execute();
                ResultSet rs = null;
                try {
                    rs = stmt.getResultSet();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                List<User> users = USER_ROW_MAPPER.getRow(rs);
                if (users.size() > 0) {
                    return users.get(0);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return null;
    }

}
