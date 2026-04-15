package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rus.cheremisin.itktasksspringmvc.entity.Order;

public interface OrderService {
    Order getOrderById(Long id);

    Order addOrder(Order order);

    Order editOrder(Long id, Order order);

    void deleteOrderById(Long id);

    Page<Order> getAllOrders(Pageable pageable);
}