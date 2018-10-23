package com.study.dao;

import com.study.entity.Product;
import com.study.security.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface UserDao {

        void update(User user);

        boolean isExisting(String user, String password);

        User getOne(String user);






}
