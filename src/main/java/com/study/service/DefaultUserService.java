package com.study.service;

import com.study.dao.UserDao;
import com.study.security.entity.User;

public class DefaultUserService {
    private UserDao userDao;

    public DefaultUserService() {
    }

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getByLogin(String login){
        return userDao.getOne(login);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
