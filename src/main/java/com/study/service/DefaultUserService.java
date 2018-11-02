package com.study.service;

import com.study.dao.UserDao;
import com.study.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService {
    @Autowired
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
