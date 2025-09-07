package sk.streetofcode.productordermanagement.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.exception.BadRequestException;
import sk.streetofcode.productordermanagement.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.order.api.dto.OrderDTO;
import sk.streetofcode.productordermanagement.order.entity.OrderEntity;
import sk.streetofcode.productordermanagement.order.repository.OrderRepository;
import sk.streetofcode.productordermanagement.product.service.ProductService;

import java.util.ArrayList;

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
        OrderEntity orderEntity = getOrderEntityById(id);

        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    @Override
    public OrderDTO createOrder() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPaid(false);
        orderEntity.setShoppingList(new ArrayList<>());

        try {
            return modelMapper.map(orderRepository.save(orderEntity), OrderDTO.class);
        } catch (Exception e) {
            throw new InternalErrorException("Error creating order " + e.getMessage());
        }
    }

    @Override
    public String payForOrder(long id) {
        OrderEntity orderEntity = getOrderEntityById(id);
        if (orderEntity.isPaid()) {
            throw new BadRequestException("Order with id " + id + " already paid");
        }

        // TODO: Optimize DB calls
        double totalPrice = orderEntity.getShoppingList().stream()
                .mapToDouble(item -> productService.getProductById(item.getProductId())
                        .getPrice() * item.getAmount())
                .sum();

        orderEntity.setPaid(true);

        return Double.toString(totalPrice);
    }

    @Override
    public void deleteOrder(long id) {
        OrderEntity orderEntity = getOrderEntityById(id);
        orderRepository.delete(orderEntity);
    }
}
