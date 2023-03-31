package ru.market.zhuravel.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.market.zhuravel.api.CartDto;
import ru.market.zhuravel.api.OrderDetailsDto;
import ru.market.zhuravel.core.entities.Order;
import ru.market.zhuravel.core.entities.OrderItem;
import ru.market.zhuravel.core.exceptions.CartIsEmptyException;
import ru.market.zhuravel.core.exceptions.ResourceNotFoundException;
import ru.market.zhuravel.core.integrations.CartServiceIntegration;
import ru.market.zhuravel.core.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartServiceIntegration cartServiceIntegration;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public void createNewOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto cart = cartServiceIntegration.getCurrentCart(username);
        if (cart.getItems().isEmpty()) {
            throw new CartIsEmptyException("Корзина пуста, невозможно создать заказ");
        }
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setTotalPrice(cart.getTotalPrice());
        order.setUsername(username);
        order.setItems(new ArrayList<>());

        cart.getItems().forEach(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setPricePerProduct(ci.getPricePerProduct());
            oi.setProduct(productService.findById(ci.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
            order.getItems().add(oi);
        });
        orderRepository.save(order);
        cartServiceIntegration.clearCart(username);
    }

    public List<Order> findUserOrders(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
