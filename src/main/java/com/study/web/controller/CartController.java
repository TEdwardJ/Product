package com.study.web.controller;

import com.study.entity.Cart;
import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.security.entity.Session;
import com.study.service.ProductService;
import com.study.web.servlet.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authService;


    @RequestMapping(path="/cart/add/{id}",method = RequestMethod.POST)
    public void addProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable int id){
        Product product = productService.getOne(id);
        if (product!=null){
            Session session =  authService.getSession(authService.getUserToken(request.getCookies()));
            Cart cart =session.getCart();
            cart.addProduct(product);
        }
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(path="/cart/",method = RequestMethod.GET)
    public void showCartList(HttpServletRequest request, HttpServletResponse response){
        Session session =  authService.getSession(authService.getUserToken(request.getCookies()));
        Cart cart =session.getCart();

        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();

        attributes.put("cartitems", cart.getContent());
        attributes.put("user", session.getUser().getLogin());
        try {
            response.getWriter().println(pageGenerator.getPage("cart.html", attributes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
