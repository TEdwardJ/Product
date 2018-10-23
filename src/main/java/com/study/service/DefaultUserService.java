package com.study.service;

import com.study.dao.UserDao;
import com.study.security.entity.User;

public class DefaultUserService {
    UserDao userDao;


    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getByLogin(String login){
        return userDao.getOne(login);
    }


}
