package com.parkjinhun.shopping.shopping_mall_project.infrastructure.order;

import com.parkjinhun.shopping.shopping_mall_project.domain.item.ItemReader;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.Order;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderCommand;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderItemSeriesFactory;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.OrderStore;
import com.parkjinhun.shopping.shopping_mall_project.domain.order.item.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemSeriesFactoryImpl implements OrderItemSeriesFactory {
    private final ItemReader itemReader;
    private final OrderStore orderStore;

    @Override
    public List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemBy(orderItemRequest.getItemToken());
                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);

                    orderItemRequest.getOrderItemOptionGroupList().forEach(orderItemOptionGroupRequest -> {
                        var initOrderItemOptionGroup = orderItemOptionGroupRequest.toEntity(orderItem);
                        var orderItemOptionGroup = orderStore.store(initOrderItemOptionGroup);

                        orderItemOptionGroupRequest.getOrderItemOptionList().forEach(orderItemOptionRequest -> {
                            var initOrderItemOption = orderItemOptionRequest.toEntity(orderItemOptionGroup);
                            orderStore.store(initOrderItemOption);
                        });
                    });
                    return orderItem;
                }).collect(Collectors.toList());
    }
}
