package com.study.security;

import com.study.dao.UserDAO;
import com.study.security.entity.Session;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    final static int MAX_AGE = 60*60*2;

    User getLoggedUser(String token);

    boolean checkLoggedUser(HttpServletRequest request);
    boolean checkLoggedUserAndRole(HttpServletRequest request, UserRole role);

    void logout(HttpServletRequest request);

    Session auth(String user, String password);

    Session getSession(HttpServletRequest request);

}
