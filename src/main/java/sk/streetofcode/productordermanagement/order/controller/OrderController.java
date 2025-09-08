package sk.streetofcode.productordermanagement.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.order.api.dto.OrderDTO;
import sk.streetofcode.productordermanagement.order.api.request.AddToOrderRequest;
import sk.streetofcode.productordermanagement.order.service.OrderService;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    ResponseEntity<OrderDTO> createOrder() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder());
    }

    @PostMapping("/order/{id}/pay")
    ResponseEntity<String> payForOrder(@PathVariable long id) {
        return ResponseEntity.ok(orderService.payForOrder(id));
    }

    @PostMapping("/order/{id}/add")
    ResponseEntity<OrderDTO> addToOrder(@PathVariable long id, @RequestBody AddToOrderRequest request) {
        return ResponseEntity.ok(orderService.addProductToOrder(id, request));
    }

    @GetMapping("/order/{id}")
    ResponseEntity<OrderDTO> getOrder(@PathVariable long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/order/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
