package com.study.dao;

import com.study.entity.Product;
import com.study.security.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface UserDAO {
        boolean open();

        void close();

        void update(User user);

        boolean isExisting(String user, String password);

        User getOne(String user);






}
