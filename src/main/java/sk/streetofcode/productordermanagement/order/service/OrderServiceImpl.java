package sk.streetofcode.productordermanagement.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.exception.BadRequestException;
import sk.streetofcode.productordermanagement.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.order.api.dto.OrderDTO;
import sk.streetofcode.productordermanagement.order.api.dto.ShoppingItem;
import sk.streetofcode.productordermanagement.order.api.request.AddToOrderRequest;
import sk.streetofcode.productordermanagement.order.entity.OrderEntity;
import sk.streetofcode.productordermanagement.order.repository.OrderRepository;
import sk.streetofcode.productordermanagement.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private OrderEntity getOrderEntityById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public OrderDTO getOrderById(long id) {
        OrderEntity order = getOrderEntityById(id);

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO createOrder() {
        OrderEntity order = new OrderEntity();
        order.setPaid(false);
        order.setShoppingList(new ArrayList<>());

        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

    @Override
    public OrderDTO addProductToOrder(long id, AddToOrderRequest request) {
        OrderEntity orderEntity = getOrderEntityById(id);
        if (orderEntity.isPaid()) {
            throw new BadRequestException("Order with id " + id + " is already paid");
        }

        // Remove products from storage
        productService.decreaseProductAmount(request.getProductId(), request.getAmount());

        // Product is present in order
        Optional<ShoppingItem> existingItem = orderEntity.getShoppingList().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Increase amount of product in order
            existingItem.get().setAmount(existingItem.get().getAmount() + request.getAmount());
        } else {
            // Add new product to order
            orderEntity.getShoppingList().add(new ShoppingItem(
                    request.getProductId(),
                    request.getAmount()
            ));
        }

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public String payForOrder(long id) {
        OrderEntity order = getOrderEntityById(id);
        if (order.isPaid()) {
            throw new BadRequestException("Order with id " + id + " already paid");
        }

        List<Long> productIds = order.getShoppingList().stream()
                .map(ShoppingItem::getProductId)
                .toList();

        // Sum total price of order
        double totalPrice = order.getShoppingList().stream()
                .mapToDouble(item -> productService.getProductById(item.getProductId())
                        .getPrice() * item.getAmount())
                .sum();

        order.setPaid(true);
        orderRepository.save(order);

        return Double.toString(totalPrice);
    }

    @Override
    public void deleteOrder(long id) {
        OrderEntity order = getOrderEntityById(id);
        orderRepository.delete(order);
    }
}
