package com.study.service;

import com.study.dao.ProductDao;
import com.study.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public DefaultProductService(ProductDao dao) {
        this.productDao = dao;
    }

    public DefaultProductService() {
    }

    @Override
    public void add(Product product) {
        productDao.insert(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void addProducts(Product... products){
        Arrays.stream(products)
                .forEach(this::add);
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = productDao.getAll();
        //list.sort((t1,t2)->t2.getId().compareTo(t1.getId()));
        return list;
    }

    public void delete(int id){
        productDao.delete(id);
    }

    public Product getOne(int id){
        return productDao.getById(id);
    }
}



