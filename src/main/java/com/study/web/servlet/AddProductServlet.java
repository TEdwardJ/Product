package com.study.web.servlet;

import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends AbstractProductServlet {


    public AddProductServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        resp.getWriter().println(pageGenerator.getPage("addProduct.html",attributes));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productService.add(new Product(
                req.getParameter("picturePath"),
                req.getParameter("name"),
                Double.valueOf(req.getParameter("price")),
                LocalDateTime.now()));
        resp.sendRedirect("/");
    }
}
