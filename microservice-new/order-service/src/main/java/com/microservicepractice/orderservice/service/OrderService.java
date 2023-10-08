package com.microservicepractice.orderservice.service;

import com.microservicepractice.orderservice.dto.InventoryResponse;
import com.microservicepractice.orderservice.dto.OrderLineItemsDto;
import com.microservicepractice.orderservice.dto.OrderRequest;
import com.microservicepractice.orderservice.model.Order;
import com.microservicepractice.orderservice.model.OrderLineItems;
import com.microservicepractice.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes= order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode).toList();
        //call inv service and place order if it is in stock
        InventoryResponse[] inventoryResponseArray= webClient.get()
                .uri("http://localhost:8082/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();
        boolean allProductsInStock=Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);

        }
        else{
            throw new IllegalArgumentException("Product is not in stock, try again later");
        }
        //orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
