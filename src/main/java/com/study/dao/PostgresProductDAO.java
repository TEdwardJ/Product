package com.study.dao;

import com.study.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresProductDAO implements ProductDAO {

    private Connection conn;
    private ProductRowMapper rowMapper = new ProductRowMapper();

    private DataSource dataSource;

    private static final String query = "SELECT * FROM shop.products";
    private static final String PRODUCT_DEL_QUERY = "DELETE FROM shop.products WHERE id = ? ";
    private static final String GET_ONE_QUERY = "SELECT  * FROM shop.products t WHERE t.id = ? ;";
    private static final String PRODUCT_UPD_QUERY = "UPDATE shop.products set name = ?, picturePath = ?, price = ?, addeddate = ? WHERE id = ? ";
    private static final String PRODUCT_INS_QUERY = "INSERT INTO shop.products(picturepath,name,price,addeddate) VALUES(?,?,?,?) ";

    public PostgresProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean open() {
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(PRODUCT_DEL_QUERY);) {
                stmt.setInt(1, id);
                System.out.println(stmt.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    @Override
    public void insert(Product product) {
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(PRODUCT_INS_QUERY);) {
                stmt.setString(1, product.getPicturePath());
                stmt.setString(2, product.getName());
                stmt.setDouble(3, product.getPrice());
                stmt.setTimestamp(4, Timestamp.valueOf(product.getAddDate()));

                System.out.println(stmt.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    @Override
    public void update(Product product) {
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(PRODUCT_UPD_QUERY);) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getPicturePath());
                stmt.setDouble(3, product.getPrice());
                stmt.setTimestamp(4, Timestamp.valueOf(product.getAddDate()));
                stmt.setInt(5, product.getId());

                System.out.println(stmt.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        if (open()) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                list = rowMapper.getRow(rs);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list;
    }

    @Override
    public Product getOne(int id) {
        List<Product> list = new ArrayList<>();
        if (open()) {
            try (PreparedStatement stmt = conn.prepareStatement(GET_ONE_QUERY);
                 ) {
                stmt.setInt(1,id);
                stmt.execute();
                ResultSet rs = stmt.getResultSet();
                list = rowMapper.getRow(rs);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list.get(0);
    }
}
