package com.parkjinhun.shopping.shopping_mall_project.application.order;

import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderCommand;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderInfo;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderService;
import com.parkjinhun.shopping.shopping_mall_project.interfaces.order.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;

    public String registerOrder(OrderCommand.RegisterOrder registerOrder, String accessToken) {
        return orderService.registerOrder(registerOrder, accessToken);
    }

    public OrderInfo.Main retrieveOrder(String accessToken, String orderToken) {
        return orderService.retrieveOrder(accessToken, orderToken);
    }

    public OrderInfo.OrderList retrieveAllOrder(String accessToken, OrderDto.SearchOrderRequest searchRequest, Pageable pageable) {
        return orderService.retrieveAllOrder(accessToken, searchRequest, pageable);
    }
}
