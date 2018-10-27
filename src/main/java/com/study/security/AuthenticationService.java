package com.study.security;

import com.study.security.entity.Session;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    final static int MAX_AGE = 60*60*2;

    boolean checkLoggedUser(Cookie[] cookies);

    boolean checkLoggedUserAndRole(Cookie[] cookies, UserRole role);

    void logout(Cookie[] cookie);

    Session auth(String user, String password);

    public String getUserToken(Cookie[] cookies);

    Session getSession(String token);

}
