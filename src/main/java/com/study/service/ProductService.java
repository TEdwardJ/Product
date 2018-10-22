package com.study.service;

import com.study.entity.Product;

import java.util.List;

public interface ProductService {

    public void add(Product product);

    public void update(Product product);

    public void addProducts(Product... products);

    public List<Product> getAll();

    public Product getOne(int id);

    public void delete(int id);

}

