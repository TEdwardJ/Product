package com.study.dao;

import com.study.security.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserDAO implements UserDAO {

    private static final String USER_UPD_QUERY = "UPDATE shop.users set hashedPassword = ?, sole = ?, role = ? WHERE \"user\" = ? ";
    private static final String GET_USER_BY_LOGIN = "SELECT \"user\" username,password,role,sole, hashedPassword FROM shop.users t WHERE t.user = ? ;";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private Connection conn;

    private DataSource dataSource;

    public PostgresUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean open() {
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(User user) {
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(USER_UPD_QUERY);) {
                stmt.setString(1, user.getPassword());
                stmt.setString(2, user.getSole());
                stmt.setString(3, user.getUserRole().toString());
                stmt.setString(4, user.getLogin());

                System.out.println(stmt.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public boolean isExisting(String user, String password) {
        return getOne(user) != null;
    }

    @Override
    public User getOne(String user) {
        List<User> list = new ArrayList<>();
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_LOGIN);) {
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
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return null;
    }


}
