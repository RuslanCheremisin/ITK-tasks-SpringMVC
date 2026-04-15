package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.OrderRepository;
import rus.cheremisin.itktasksspringmvc.entity.Order;
import rus.cheremisin.itktasksspringmvc.exception.OrderNotFoundException;
import rus.cheremisin.itktasksspringmvc.service.OrderService;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("pageable is null!");
        }
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException("no order with such ID");
        }
        return orderOptional.get();
    }

    @Override
    public Order addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException("cannot add null order");
        }
        return orderRepository.save(order);
    }


    @Override
    public Order editOrder(Long id, Order updatedOrder) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException("no order with such ID");
        }
        Order existingOrder = orderOptional.get();
        if (updatedOrder != null) {
            existingOrder.setCustomer(updatedOrder.getCustomer());
            existingOrder.setProducts(updatedOrder.getProducts());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setOrderStatus(updatedOrder.getOrderStatus());

            return orderRepository.save(existingOrder);
        } else {
            throw new NullPointerException("cannot update from null order");
        }
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException("no order with such ID");
        }
        orderRepository.delete(orderOptional.get());
    }
}
