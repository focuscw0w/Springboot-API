package sk.streetofcode.productordermanagement.order.service;

import sk.streetofcode.productordermanagement.order.api.dto.OrderDTO;

public interface OrderService {
    // GET
    OrderDTO getOrderById(long id);

    // POST
    OrderDTO createOrder();
    String payForOrder(long id);

    // DELETE
    void deleteOrder(long id);
}
