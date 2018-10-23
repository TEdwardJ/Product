package com.study.dao;

import com.study.entity.Product;

import java.util.List;

public interface ProductDao {

    void delete(int id);

    void insert(Product product);

    void update(Product product);

    List<Product> getAll();
    Product getById(int id);
}




