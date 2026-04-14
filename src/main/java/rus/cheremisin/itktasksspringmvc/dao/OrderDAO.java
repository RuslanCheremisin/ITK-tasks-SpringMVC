package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rus.cheremisin.itktasksspringmvc.entity.ShoppingOrder;

public interface OrderDAO extends JpaRepository<ShoppingOrder, Long> {
}
