package com.andreip.db;

import com.andreip.model.Product;

import java.util.List;

/**
 * Products DAO
 */
public interface ProductDao {
    /**
     * Returns all found products in the specified range
     * @param limit     how many items to return
     * @param offset    first row in DB to return
     * @return  List of products
     */
    List<Product> findAll(int limit, int offset);
    /**
     * Finds related products for the current specified product id
     * @param productId product id to find related
     * @return  list of retaed products
     */
    List<Product> findRelated(String productId);
    /**
     * Returns count of the products in the DB
     * @return  number of products
     */
    int getCount();
}
