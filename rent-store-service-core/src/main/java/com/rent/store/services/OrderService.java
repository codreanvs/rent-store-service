package com.rent.store.services;

import com.rent.store.models.dtos.OrderDTO;

public interface OrderService {

    OrderDTO store(OrderDTO orderDTO);

    OrderDTO get(String uuid);

}
