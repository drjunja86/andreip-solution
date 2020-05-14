package com.andreip.db;

import com.andreip.model.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Order data access object implementation
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    public OrderDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    NamedParameterJdbcTemplate template;

    /**
     * Returns all found orders in the specified range
     * @param limit     how many items to return
     * @param offset    first row in DB to return
     * @return  List of orders
     */
    @Override
    public List<Order> findAll(int limit, int offset) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return template.query("SELECT * FROM orders LIMIT :limit OFFSET :offset",
                param, new OrderRowMapper());
    }

    /**
     * Returns count of the orders in the DB
     * @return  number of orders
     */
    @Override
    public int getCount() {
        SqlParameterSource param = new MapSqlParameterSource();
        //noinspection ConstantConditions
        return template.queryForObject("SELECT COUNT(*) FROM orders", param, Integer.class);
    }
}
