package com.study.web.servlet;

import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductInfoServlet extends AbstractProductServlet {


    public ProductInfoServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            PageGenerator pageGenerator = PageGenerator.getInstance();
            Map<String, Object> attributes = new HashMap();
            int id = Integer.valueOf(req.getParameter("id"));
            Product product = productService.getOne(id);
            attributes.put("product", product);
            resp.getWriter().println(pageGenerator.getPage("productInfo.html", attributes));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
    }

}
