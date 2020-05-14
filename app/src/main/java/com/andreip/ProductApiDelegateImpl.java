package com.andreip;

import com.andreip.api.ProductApiDelegate;
import com.andreip.db.ProductDao;
import com.andreip.model.ArrayOfProducts;
import com.andreip.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductApiDelegateImpl implements ProductApiDelegate {

    @Resource
    ProductDao productService;

    @Override
    public ResponseEntity<ArrayOfProducts> getProducts(Integer page, Integer items) {
        items = items == null ? 10 : items;
        page = page == null ? 1 : page;
        int totalItems = productService.getCount();
        int totalPages = totalItems / items;
        if (totalPages == 0) totalPages = 1; // in case there less than 1 page
        else if (totalItems % items > 0) totalPages++;
        if (page > totalPages) page = totalPages;

        List<Product> dbProducts = productService.findAll(items, (page - 1) * items);
        ArrayOfProducts arrayOfProducts = new ArrayOfProducts();
        arrayOfProducts.setPage(page);
        arrayOfProducts.setItemsPerPage(items);
        arrayOfProducts.setTotalPages(totalPages);
        arrayOfProducts.setTotalItems(totalItems);
        arrayOfProducts.setItems(dbProducts);
        return ResponseEntity.ok(arrayOfProducts);
    }

    @Override
    public ResponseEntity<List<Product>> getRelatedProducts(String productId) {
        List<Product> related = productService.findRelated(productId);
        return ResponseEntity.ok(related);
    }
}
