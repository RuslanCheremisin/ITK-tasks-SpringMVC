package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rus.cheremisin.itktasksspringmvc.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long> {
}
