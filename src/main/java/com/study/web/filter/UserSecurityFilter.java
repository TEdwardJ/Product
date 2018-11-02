package com.study.web.filter;

import com.study.security.AuthenticationService;
import com.study.security.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSecurityFilter implements Filter {
    private AuthenticationService authService;

    public UserSecurityFilter() {
    }

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        boolean isAuth = authService.checkLoggedUserAndRole(httpServletRequest.getCookies(), UserRole.USER)||
                authService.checkLoggedUserAndRole(httpServletRequest.getCookies(), UserRole.ADMIN);

        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        AuthenticationService authService = (AuthenticationService) WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean("authenticationService");
        this.authService = authService;
    }

    @Override
    public void destroy() {

    }
}
