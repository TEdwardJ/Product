package com.study.dao;

import com.study.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final String ALL_PRODUCTS_QUERY = "SELECT * FROM shop.products";
    private static final String PRODUCT_DEL_QUERY = "DELETE FROM shop.products WHERE id = ? ";
    private static final String GET_ONE_QUERY = "SELECT  * FROM shop.products t WHERE t.id = ? ;";
    private static final String PRODUCT_UPD_QUERY = "UPDATE shop.products set name = ?, picturePath = ?, price = ?, addeddate = ? WHERE id = ? ";
    private static final String PRODUCT_INS_QUERY = "INSERT INTO shop.products(picturepath,name,price,addeddate) VALUES(?,?,?,?) ";

    private ProductRowMapper rowMapper = new ProductRowMapper();

    private DataSource dataSource;


    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcProductDao() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void delete(int id) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(PRODUCT_DEL_QUERY);) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }

    @Override
    public void insert(Product product) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(PRODUCT_INS_QUERY);) {
                stmt.setString(1, product.getPicturePath());
                stmt.setString(2, product.getName());
                stmt.setDouble(3, product.getPrice());
                stmt.setTimestamp(4, Timestamp.valueOf(product.getAddDate()));

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }

    @Override
    public void update(Product product) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(PRODUCT_UPD_QUERY);) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getPicturePath());
                stmt.setDouble(3, product.getPrice());
                stmt.setTimestamp(4, Timestamp.valueOf(product.getAddDate()));
                stmt.setInt(5, product.getId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
            try (Connection connection = dataSource.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(ALL_PRODUCTS_QUERY)) {
                list = rowMapper.getRows(rs);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return list;
    }

    @Override
    public Product getById(int id) {
        Product product;
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(GET_ONE_QUERY);       ) {
                stmt.setInt(1,id);
                stmt.execute();
                ResultSet resultSet = stmt.getResultSet();
                product = rowMapper.getRow(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return product;
    }
}
