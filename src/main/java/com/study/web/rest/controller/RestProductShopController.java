package com.study.web.rest.controller;

import com.study.entity.Product;
import com.study.security.AuthenticationService;
import com.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
//@RequestMapping(path="/api/v1")
public class RestProductShopController {

    @Autowired
    AuthenticationService authService;

    @Autowired
    ProductService productService;

    public RestProductShopController() {
    }

    @GetMapping(path = {"/api/v1/test","/api/v1"})
    public String getTest(){
        return "test";
    }



    @RequestMapping(path = "products", method = RequestMethod.GET)
    public List<Product> getProductsList(HttpServletRequest req){
        System.out.println(req.getRequestURL());
        return productService.getAll();
    }


    @GetMapping(path = "product/{id}")
    public Product getProductById2(@PathVariable(name = "id") Integer id){
        Product product = productService.getOne(id);
        return product;
    }

    @DeleteMapping(path = "product/{id}")
    public void deleteProductById(@PathVariable int id, HttpServletResponse response){
        productService.delete(id);
        //response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping(path = "product", method = RequestMethod.POST)
    public void addProduct(HttpServletResponse response,
                              @RequestParam int id,
                              @RequestParam String name,
                              @RequestParam String picturePath,
                              @RequestParam double price){
        Product newProduct = new Product(
                picturePath,
                name,
                Double.valueOf(price),
                LocalDateTime.now());
        productService.add(newProduct);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @RequestMapping(path = "product/{id}", method = RequestMethod.PUT)
    public void updateProduct(HttpServletResponse response,
                              @RequestParam int id,
                              @RequestParam String name,
                              @RequestParam String picturePath,
                              @RequestParam double price){
        Product product = productService.getOne(id);
        if (product == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Product newProduct = new Product(
                id,
                picturePath,
                name,
                Double.valueOf(price),
                LocalDateTime.now());
        productService.update(newProduct);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
