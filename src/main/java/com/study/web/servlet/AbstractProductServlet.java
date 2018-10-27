package com.study.web.servlet;

import com.study.security.AuthenticationService;
import com.study.service.ProductService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractProductServlet extends HttpServlet {
    private ProductService productService;

    public AbstractProductServlet() {
    }

    ProductService getProductService(){
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public AbstractProductServlet(ProductService productService) {
        this.productService = productService;
    }
}
