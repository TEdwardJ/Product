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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductEditServlet extends AbstractProductServlet {
    private static final Pattern URL_ID_PATTERN = Pattern.compile("(?<id>\\d+)$");

    public ProductEditServlet(ProductService productService) {
        super(productService);
    }


    private Integer getIdFromUrl(String url) {
        Matcher m = URL_ID_PATTERN.matcher(url);
        if(m.find()){
            return Integer.valueOf(m.group("id"));
        }
        return null;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        Product product = productService.getOne(id);
        attributes.put("product",product);
        resp.getWriter().println(pageGenerator.getPage("addProduct.html",attributes));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getIdFromUrl(req.getRequestURL().toString());
        productService.update(new Product(Integer.valueOf(req.getParameter("id")),
                req.getParameter("picturePath"),
                req.getParameter("name"),
                Double.valueOf(req.getParameter("price")),
                LocalDateTime.now()));
        resp.sendRedirect("/");
    }

}
