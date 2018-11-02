package com.study.controller;

import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.security.entity.Session;
import com.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductShopController {

    @Autowired
    AuthenticationService authService;

    @Autowired
    ProductService productService;

    @RequestMapping(path = {"/product/delete/{id}"}, method = RequestMethod.POST)
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = {"/product/add"}, method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request) {
        productService.add(new Product(
                request.getParameter("picturePath"),
                request.getParameter("name"),
                Double.valueOf(request.getParameter("price")),
                LocalDateTime.now()));
        return "redirect:/";
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.POST)
    public String loginPost(@RequestParam String login, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        Session currentSession = authService.auth(login, password);
        if (currentSession != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", login);

            Cookie cookie = new Cookie("user-token", currentSession.getToken());
            cookie.setMaxAge(AuthenticationService.MAX_AGE);
            response.addCookie(cookie);

            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (request.getParameter("logout") != null) {
            authService.logout(request.getCookies());
        }
        return "login.html";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String productList(Model model) {
        List<Product> list = productService.getAll();
        model.addAttribute("products", productService.getAll());
        return "index.html";

    }

    @RequestMapping(path = "/product/info", method = RequestMethod.GET)
    public String productInfo(@RequestParam Integer id, Model model) {
        Product product = productService.getOne(id);
        model.addAttribute("product", product);

       return "productInfo.html";
    }


    @RequestMapping(path = "/product/add/*", method = RequestMethod.GET)
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProduct.html";
    }


    @RequestMapping(path = "/product/edit", method = RequestMethod.GET)
    public String editProductForm(@RequestParam Integer id, Model model) {
        Product product = productService.getOne(id);
        model.addAttribute("product", product);
        return "addProduct.html";
    }


    @RequestMapping(path = "/product/edit", method = RequestMethod.POST)
    public String saveProductForm(HttpServletRequest req, Model model) {
        if (req.getParameter("id").isEmpty()) {
            productService.add(new Product(
                    req.getParameter("picturePath"),
                    req.getParameter("name"),
                    Double.valueOf(req.getParameter("price")),
                    LocalDateTime.now()));
        }
        else{
            productService.update(new Product(Integer.valueOf(req.getParameter("id")),
                    req.getParameter("picturePath"),
                    req.getParameter("name"),
                    Double.valueOf(req.getParameter("price")),
                    LocalDateTime.now()));
        }
        return "redirect:/";
    }
}
