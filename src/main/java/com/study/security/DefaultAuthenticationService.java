package com.study.security;

import com.study.security.entity.Session;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;
import com.study.service.DefaultUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class DefaultAuthenticationService implements AuthenticationService {

    @Autowired
    private DefaultUserService userService;
    private Map<String, Session> userSessionList = new HashMap<>();

    public DefaultAuthenticationService() {
    }

    public DefaultAuthenticationService(DefaultUserService userService) {
        this.userService = userService;
    }

    public void setUserService(DefaultUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean checkLoggedUser(Cookie[] cookies) {
        return checkLoggedUserAndRole(cookies, null);
    }

    public boolean checkLoggedUserAndRole(Cookie[] cookies, UserRole role) {
        String userToken = getUserToken(cookies);
        Session session = getSession(userToken);
        if (session != null) {
            User user = session.getUser();
            UserRole userRole = user.getUserRole();
            return role==null||role.equals(userRole);
        }
        return false;
    }

    @Override
    public void logout(Cookie cookie) {
        String token = cookie.getValue();
        userSessionList.remove(token);
    }

    public String getUserToken(Cookie[] cookies) {
        String token = "";
        if (cookies != null) {
            token = Arrays.stream(cookies)
                    .filter(t -> t.getName().equals("user-token"))
                    .filter(t -> userSessionList.keySet().contains(t.getValue()))
                    .map(t -> t.getValue())
                    .findFirst().orElse("");
            return revalidateToken(token);
        }
        return token;
    }

    private String revalidateToken(String token) {
        if (!token.isEmpty() && userSessionList.get(token).getExpireTime().isBefore(LocalDateTime.now())) {
            userSessionList.remove(token);
            return "";
        }
        return token;
    }


    public Session auth(String login, String password) {
        User userRecord = userService.getByLogin(login);
        if (userRecord != null) {
            String enteredPassword = md5Apache(password + userRecord.getSole());
            if (enteredPassword.equals(userRecord.getPassword())) {
                userRecord.setPassword("***");
                Session session = new Session();
                session.setUser(userRecord);
                session.setExpireTime(LocalDateTime.now().plusSeconds(MAX_AGE));
                session.setToken(UUID.randomUUID().toString());
                userSessionList.put(session.getToken(), session);
                return session;
            }
        }
        return null;
    }

    @Override
    public Session getSession(String token) {
        return userSessionList.get(token);
    }


    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}
