package com.study.security;

import com.study.dao.UserDAO;
import com.study.security.entity.Session;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DBAuthenticationService implements AuthenticationService {


    private UserDAO userDAO;
    private Map<String, Session> userSessionList = new HashMap<>();

    public DBAuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private User getCredetials(String user) {
        return userDAO.getOne(user);
    }

    @Override
    public User getLoggedUser(String token) {
        return userSessionList.get(token).getUser();
    }


    @Override
    public boolean checkLoggedUser(HttpServletRequest request) {
        return checkToken(request.getCookies());
    }

    public boolean checkLoggedUserAndRole(HttpServletRequest request, UserRole role) {
        String userToken = getUserToken(request.getCookies());
        if (!userToken.isEmpty()) {
            User user = getLoggedUser(userToken);
            if (user != null) {
                UserRole userRole = user.getUserRole();
                return checkToken(request.getCookies()) &&
                        role.equals(userRole);
            }
        }
        return false;
    }

    private boolean checkToken(Cookie[] cookies) {
        String userToken = getUserToken(cookies);
        if (userToken != null)
            return userSessionList.get(userToken) != null;
        return false;
    }



    @Override
    public void logout(HttpServletRequest request) {
        String token = getUserToken(request.getCookies());
        if (userSessionList.get(token).equals(getLoggedUser(token))) {
            userSessionList.remove(token);
            request.getSession().removeAttribute("user_login");
        }

    }

    private String getUserToken(Cookie[] cookies) {
        String token = "";
        if (cookies != null) {
            token = Arrays.stream(cookies)
                    .filter(t -> t.getName().equals("user-token"))
                    .filter(t -> userSessionList.keySet().contains(t.getValue()))
                    .map(t -> t.getValue())
                    .findFirst().orElse("");
            return revalidateToken(token);
        }
        return "";
    }

    private String revalidateToken(String token) {
        if (!token.isEmpty() && userSessionList.get(token).getExpireTime().isBefore(LocalDateTime.now())) {
            userSessionList.remove(token);
            return "";
        }
        return token;
    }


    public Session auth(String user, String password) {
        User userRecord;
        Session session;
        if ((userRecord = getCredetials(user)) != null) {
            String enteredPassword =md5Apache(password + userRecord.getSole());
            if (enteredPassword.equals(userRecord.getPassword())) {
                userRecord.setPassword("***");
                session = new Session();
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
    public Session getSession(HttpServletRequest request) {
        return userSessionList.get(getUserToken(request.getCookies()));
    }


    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);

        return md5Hex;
    }
}
