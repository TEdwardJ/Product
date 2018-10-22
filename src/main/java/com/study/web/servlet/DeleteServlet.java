package com.study.web.servlet;

import com.study.security.AuthenticationService;
import com.study.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteServlet extends AbstractProductServlet {


    private static final Pattern URL_ID_PATTERN = Pattern.compile("(?<id>\\d+)$");

    public DeleteServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = getIdFromUrl(req.getRequestURL().toString());
        productService.delete(id);
        resp.sendRedirect("/");
    }

    private Integer getIdFromUrl(String url) {
        Matcher m = URL_ID_PATTERN.matcher(url);
        if(m.find()){
            return Integer.valueOf(m.group("id"));
        }
        return null;
     }
}
