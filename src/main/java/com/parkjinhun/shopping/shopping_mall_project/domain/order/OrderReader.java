package com.parkjinhun.shopping.shopping_mall_project.domain.order;

import com.parkjinhun.shopping.shopping_mall_project.interfaces.order.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReader {
    Order getOrder(String orderToken);

    Page<Order> getOrderList(String memberId, OrderDto.SearchOrderRequest searchRequest, Pageable pageable);
}
