package com.andreip.db;

import com.andreip.model.OrderLine;

import java.util.List;

/**
 * Data access object for one order line in the order
 */
public interface OrderLineDao {
    /**
     * Returns all order lines for one specific order
     * @param orderId   unique order id
     * @return  list of order lines
     */
    List<OrderLine> getForOrder(Long orderId);
}
