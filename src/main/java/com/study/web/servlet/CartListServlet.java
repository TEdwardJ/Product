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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartListServlet extends AbstractProductServlet {

    private AuthenticationService authService;

    public CartListServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session =  authService.getSession(req);
        Cart cart =session.getCart();

        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        List<Product> list = productService.getAll();

        attributes.put("cartitems", cart.getContent());
        attributes.put("user", session.getUser().getLogin());
        resp.getWriter().println(pageGenerator.getPage("cart.html", attributes));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }
}
