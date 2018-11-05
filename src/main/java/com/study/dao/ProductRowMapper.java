package com.study.dao;

import com.study.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ProductRowMapper {


    public List<Product> getRows(ResultSet resultSet) throws SQLException {
        List<Product> list = new ArrayList<>();
        while (resultSet.next()){
            Product product = mapProduct(resultSet);
            list.add(product);
        }
        return list;
    }

    public Product getRow(ResultSet resultSet) throws SQLException {
        Product product;
            product = mapProduct(resultSet);
            return product;
    }

    private Product mapProduct(ResultSet resultSet) throws SQLException {
        Product product;
        product = new Product(Integer.valueOf(resultSet.getString("id")),
                resultSet.getString("picturePath"),
                resultSet.getString("name"),
                Double.valueOf(resultSet.getString("price")),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("addedDate").getTime()), ZoneId.systemDefault()));
        return product;
    }
}
