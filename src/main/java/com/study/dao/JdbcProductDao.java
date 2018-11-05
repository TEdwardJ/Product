package com.study.dao;

import com.study.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    @Autowired
    private String productSelectQuery;
    @Autowired
    private String productDeleteQuery;
    @Autowired
    private String productSelectByIdQuery;
    @Autowired
    private String productUpdateQuery;
    @Autowired
    private String productInsertQuery;

    private ProductRowMapper rowMapper = new ProductRowMapper();

    @Autowired
    private DataSource dataSource;

    JdbcTemplate jdbcTemplate;


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
             PreparedStatement stmt = connection.prepareStatement(productDeleteQuery);) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Product product) {
        jdbcTemplate.update(productInsertQuery,
                new Object[]{product.getName(),product.getPicturePath(),product.getPrice(),Timestamp.valueOf(product.getAddDate())});

    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(productUpdateQuery,
                new Object[]{product.getName(),product.getPicturePath(),product.getPrice(),Timestamp.valueOf(product.getAddDate()),product.getId()});
    }

    @Override
    public List<Product> getAll() {
        final List<Product> list =

                jdbcTemplate.query(
                        productSelectQuery,
                        (rs, rowNum) -> rowMapper.getRow(rs)
                );

        return list;
    }

    @Override
    public Product getById(int id) {
        Product product =

        jdbcTemplate.query(
                productSelectByIdQuery,
                new Integer[] {new Integer(id)},
                rs -> (rs.next())?rowMapper.getRow(rs):null
        );
        return product;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
