package com.study.controller;

import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.security.entity.Session;
import com.study.service.ProductService;
import com.study.web.servlet.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductShopController {

    @Autowired
    AuthenticationService authService;

    @Autowired
    ProductService productService;

    @RequestMapping(path={"/WEB-INF/product/delete/{id}"},method = RequestMethod.POST)
    public void deleteProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable int id){
        productService.delete(id);
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path={"/WEB-INF/product/add"},method = RequestMethod.POST)
    public void addProduct(HttpServletRequest request, HttpServletResponse response){
        productService.add(new Product(
                request.getParameter("picturePath"),
                request.getParameter("name"),
                Double.valueOf(request.getParameter("price")),
                LocalDateTime.now()));
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path={"/login"},method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response){
        String user = request.getParameter("login");
        String password = request.getParameter("password");
        Session currentSession = authService.auth(user,password);
        if (currentSession!=null){
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", user);

            Cookie cookie = new Cookie("user-token", currentSession.getToken());
            cookie.setMaxAge(AuthenticationService.MAX_AGE);
            response.addCookie(cookie);

            return "redirect:/";
        } else{
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "redirect:/login";
        }
    }

    @RequestMapping(path={"/login"},method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
            //PageGenerator pageGenerator = PageGenerator.getInstance();
        if (request.getParameter("logout")!=null){
            authService.logout(request.getCookies());
        }
    return "login.html";
    }

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String productList(HttpServletResponse resp){

        //PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        List<Product> list = productService.getAll();

        attributes.put("products", list);
        return "index.html";

    }

    @ResponseBody
    @RequestMapping(path = "/WEB-INF/product/info",method = RequestMethod.GET)
    public void productInfo(HttpServletRequest req,HttpServletResponse resp){
        int id = Integer.valueOf(req.getParameter("id"));
        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        Product product = productService.getOne(id);
        attributes.put("WEB-INF/product", product);

        try {
            resp.getWriter().println(pageGenerator.getPage("productInfo.html", attributes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @ResponseBody
    @RequestMapping(path = "/WEB-INF/product/add/*",method = RequestMethod.GET)
    public void addProductForm(HttpServletRequest req,HttpServletResponse resp){
        PageGenerator pageGenerator = PageGenerator.getInstance();
        Map<String, Object> attributes = new HashMap();
        try {
            resp.getWriter().println(pageGenerator.getPage("addProduct.html",attributes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
