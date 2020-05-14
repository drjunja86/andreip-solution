package com.andreip.db;

import com.andreip.model.OrderLine;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLineRowMapper implements RowMapper<OrderLine> {
    /**
     * Maps database row values to the OrderLine object
     * @param rs    result set from the database
     * @return      OrderLine object
     * @throws SQLException throws this exception
     */
    @Override
    public OrderLine mapRow(ResultSet rs, int i) throws SQLException {
        OrderLine orderLine = new OrderLine();
        orderLine.setProductId(rs.getString("productId"));
        orderLine.setAmount(rs.getInt("amount"));
        orderLine.setLineSubtotal(rs.getBigDecimal("subtotal"));
        orderLine.setOrderId(rs.getLong("orderId"));
        return orderLine;
    }
}
