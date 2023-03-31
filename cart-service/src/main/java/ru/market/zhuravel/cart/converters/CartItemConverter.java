package ru.market.zhuravel.cart.converters;

import org.springframework.stereotype.Component;
import ru.market.zhuravel.api.CartItemDto;
import ru.market.zhuravel.cart.utils.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
