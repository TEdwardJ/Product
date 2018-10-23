package com.study.service;

import com.study.dao.ProductDao;
import com.study.entity.Product;

import java.util.Arrays;
import java.util.List;

public class DefaultProductService implements ProductService {


    private ProductDao dao;

    public DefaultProductService(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(Product product) {
        dao.insert(product);
    }

    @Override
    public void update(Product product) {
        dao.update(product);
    }

    @Override
    public void addProducts(Product... products){
        Arrays.stream(products)
                .forEach(this::add);
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = dao.getAll();
        //list.sort((t1,t2)->t2.getId().compareTo(t1.getId()));
        return list;
    }

    public void delete(int id){
        dao.delete(id);
    }

    public Product getOne(int id){
        return dao.getById(id);
    }
}



