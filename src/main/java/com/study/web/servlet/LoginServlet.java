package com.study.web.servlet;


import com.study.security.AuthenticationService;
import com.study.security.DBAuthenticationService;
import com.study.security.entity.Session;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private AuthenticationService authService;

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("login");
        String password = request.getParameter("password");
        Session currentSession = authService.auth(user,password);
        if (currentSession!=null){
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", user);

            Cookie cookie = new Cookie("user-token", currentSession.getToken());
            cookie.setMaxAge(AuthenticationService.MAX_AGE);
            response.addCookie(cookie);

            response.sendRedirect("/");
        } else{
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/login/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getRequestURL());
        HttpSession session = request.getSession(false);
        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        if (request.getParameter("logout")!=null){
            authService.logout(request);
        }

        response.getWriter().println(pageGenerator.getPage("login.html",attributes));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
