package com.study.web.servlet;

import com.study.security.AuthenticationService;
import com.study.service.ProductService;

import javax.servlet.http.HttpServlet;

public class AbstractProductServlet extends HttpServlet {
    ProductService productService;

    public AbstractProductServlet(ProductService productService) {
        this.productService = productService;
    }
}
