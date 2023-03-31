package ru.market.zhuravel.core.converters;

import org.springframework.stereotype.Component;
import ru.market.zhuravel.api.OrderItemDto;
import ru.market.zhuravel.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(
                o.getProduct().getId(),
                o.getProduct().getTitle(),
                o.getQuantity(),
                o.getPricePerProduct(),
                o.getPrice());
    }
}
