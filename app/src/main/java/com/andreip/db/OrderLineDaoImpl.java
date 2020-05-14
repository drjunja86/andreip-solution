package com.andreip.db;

import com.andreip.model.OrderLine;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderLineDaoImpl implements OrderLineDao {

    public OrderLineDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    NamedParameterJdbcTemplate template;

    /**
     * Returns all order lines for one specific order
     * @param orderId   unique order id
     * @return  list of order lines
     */
    @Override
    public List<OrderLine> getForOrder(Long orderId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("orderId", orderId);

        return template.query("SELECT * FROM lines WHERE orderId=:orderId",
                param, new OrderLineRowMapper());
    }
}
