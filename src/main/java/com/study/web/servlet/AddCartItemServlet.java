package com.study.web.servlet;

import com.study.entity.Cart;
import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.security.entity.Session;
import com.study.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCartItemServlet extends AbstractProductServlet {

    private AuthenticationService authService;
    private static final Pattern URL_ID_PATTERN = Pattern.compile("(?<id>\\d+)$");
    public AddCartItemServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getIdFromUrl(req.getRequestURL().toString());
        Product product = productService.getOne(id);
        if (product!=null){
            Session session =  authService.getSession(req);
            Cart cart =session.getCart();
            cart.addProduct(product);
        }
        resp.sendRedirect("/");
    }

    private Integer getIdFromUrl(String url) {
        Matcher m = URL_ID_PATTERN.matcher(url);
        if(m.find()){
            return Integer.valueOf(m.group("id"));
        }
        return null;
    }


    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }
}
