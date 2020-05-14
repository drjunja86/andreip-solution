package com.andreip.db;

import com.andreip.model.Product;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product data access object implementation
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    public ProductDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    NamedParameterJdbcTemplate template;

    /**
     * Returns all found products in the specified range
     * @param limit     how many items to return
     * @param offset    first row in DB to return
     * @return  List of products
     */
    @Override
    public List<Product> findAll(int limit, int offset) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return template.query("SELECT * FROM product LIMIT :limit OFFSET :offset",
                param, new ProductRowMapper());
    }

    /**
     * Finds related products for the current specified product id
     * @param productId product id to find related
     * @return  list of retaed products
     */
    @Override
    public List<Product> findRelated(String productId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("productId", productId);
        String sql = "SELECT * FROM product\n" +
                "WHERE productId IN (\n" +
                "SELECT productId\n" +
                "FROM lines\n" +
                "WHERE orderId IN " +
                "(SELECT orderId FROM lines WHERE productId=:productId) " +
                "AND productId != :productId\n" +
                "GROUP BY productId\n" +
                "ORDER BY COUNT(productId) DESC)";
        return template.query(sql, param, new ProductRowMapper());
    }

    /**
     * Returns count of the products in the DB
     * @return  number of products
     */
    @Override
    public int getCount() {
        SqlParameterSource param = new MapSqlParameterSource();
        //noinspection ConstantConditions
        return template.queryForObject("SELECT COUNT(*) FROM product", param, Integer.class);
    }

}
