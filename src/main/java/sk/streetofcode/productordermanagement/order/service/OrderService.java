package sk.streetofcode.productordermanagement.order.service;

import sk.streetofcode.productordermanagement.order.api.dto.OrderDTO;
import sk.streetofcode.productordermanagement.order.api.request.AddToOrderRequest;

public interface OrderService {
    // GET
    OrderDTO getOrderById(long id);

    // POST
    OrderDTO createOrder();
    OrderDTO addProductToOrder(long id, AddToOrderRequest request);
    String payForOrder(long id);

    // DELETE
    void deleteOrder(long id);
}
