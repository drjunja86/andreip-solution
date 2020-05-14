package com.andreip.db;

import com.andreip.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    /**
     * Maps database row values to the Product object
     * @param rs    result set from the database
     * @return      Product object
     * @throws SQLException throws this exception
     */
    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("productId"));
        product.setName(rs.getString("productName"));
        product.setStock(rs.getInt("stock"));
        product.setPrice(rs.getBigDecimal("price"));
        return product;
    }
}
