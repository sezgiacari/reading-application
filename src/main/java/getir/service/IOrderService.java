package getir.service;

import getir.entity.Order;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface IOrderService {

    Order createNewOrder(Order order);

    Order getOrderById(String orderId);

    List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> getOrdersByCustomerId(String customerId, Pageable pageable);
}
