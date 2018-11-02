package com.study.web.controller;

import com.study.entity.Cart;
import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.security.entity.Session;
import com.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authService;


    @RequestMapping(path="/cart/add/{id}",method = RequestMethod.POST)
    public String addProduct(@CookieValue("user-token") String cookie, @PathVariable int id){
        Product product = productService.getOne(id);
        if (product!=null){
            Session session =  authService.getSession(cookie);
            Cart cart =session.getCart();
            cart.addProduct(product);
        }
        return "redirect:/cart/";
    }

    @RequestMapping(path="/cart/",method = RequestMethod.GET)
    public String showCartList(@CookieValue("user-token") String cookie, Model model){
        Session session =  authService.getSession(cookie);
        Cart cart =session.getCart();

        model.addAttribute("cart", cart);
        model.addAttribute("user", session.getUser().getLogin());
        return "cart.html";
    }
}
