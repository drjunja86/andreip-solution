package com.andreip;

import com.andreip.api.OrderApi;
import com.andreip.api.OrderApiDelegate;
import com.andreip.db.OrderDao;
import com.andreip.db.OrderLineDao;
import com.andreip.model.ArrayOfOrders;
import com.andreip.model.Order;
import com.andreip.model.OrderLine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderApiDelegateImpl implements OrderApiDelegate {

    @Resource
    OrderDao orderService;

    @Resource
    OrderLineDao orderLineService;

    /**
     * GET /order : Get list of all orders
     *
     * @param page The index of the page to display starting from 1. (optional, default to 1)
     * @param items The number of orders to display in one page. Minimunm is 1 and maximum is 100, default is 10 (optional, default to 10)
     * @return successful operation (status code 200)
     *         or Generic error (status code 200)
     * @see OrderApi#getOrders
     */
    @Override
    public ResponseEntity<ArrayOfOrders> getOrders(Integer page, Integer items) {
        // pagination logic
        items = items == null ? 10 : items;
        page = page == null ? 1 : page;
        int totalItems = orderService.getCount();
        int totalPages = totalItems / items;
        if (totalPages == 0) totalPages = 1; // in case there less items than 1 page may contain
        else if (totalItems % items > 0) totalPages++;
        if (page > totalPages) page = totalPages;

        List<Order> dbOrders = orderService.findAll(items, (page - 1) * items);

        for (Order order : dbOrders) {
            List<OrderLine> orderLines = orderLineService.getForOrder(order.getId());
            order.setOrderLines(orderLines);
        }

        ArrayOfOrders arrayOfOrders = new ArrayOfOrders();
        arrayOfOrders.setPage(page);
        arrayOfOrders.setItemsPerPage(items);
        arrayOfOrders.setTotalPages(totalPages);
        arrayOfOrders.setTotalItems(totalItems);
        arrayOfOrders.setItems(dbOrders);

        return ResponseEntity.ok(arrayOfOrders);
    }
}
