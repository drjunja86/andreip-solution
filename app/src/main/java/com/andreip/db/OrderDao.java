package com.andreip.db;

import com.andreip.model.Order;

import java.util.List;

/**
 * Order data access object definition
 */
public interface OrderDao {
    /**
     * Returns all found orders in the specified range
     * @param limit     how many items to return
     * @param offset    first row in DB to return
     * @return  List of orders
     */
    List<Order> findAll(int limit, int offset);
    /**
     * Returns count of the orders in the DB
     * @return  number of orders
     */
    int getCount();
}
