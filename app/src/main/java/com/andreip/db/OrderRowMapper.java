package com.andreip.db;

import com.andreip.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class OrderRowMapper implements RowMapper<Order> {

    /**
     * Maps database row values to the Order object
     * @param rs    result set from the database
     * @return      Order object
     * @throws SQLException throws this exception
     */
    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        OffsetDateTime odtRetrieved = rs.getObject("purchaseDate", OffsetDateTime.class);
        order.setPurchaseDate(odtRetrieved);
        order.setTotal(rs.getBigDecimal("total"));
        return order;
    }
}
