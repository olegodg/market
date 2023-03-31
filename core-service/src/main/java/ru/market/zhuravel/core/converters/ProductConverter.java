package ru.market.zhuravel.core.converters;

import org.springframework.stereotype.Component;
import ru.market.zhuravel.api.ProductDto;
import ru.market.zhuravel.core.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }
}
