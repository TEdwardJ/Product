package com.study.web.servlet;

import com.study.entity.Product;
import com.study.service.ProductService;
import com.study.web.servlet.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListServlet extends AbstractProductServlet {

    public ProductListServlet() {
        super();
    }

    public ProductListServlet(ProductService productService) {
        super(productService);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);

            PageGenerator pageGenerator = PageGenerator.getInstance();
            Map<String, Object> attributes = new HashMap();
            List<Product> list = getProductService().getAll();

            attributes.put("products", list);
            resp.getWriter().println(pageGenerator.getPage("index.html", attributes));

    }
}
