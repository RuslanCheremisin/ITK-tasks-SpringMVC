package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rus.cheremisin.itktasksspringmvc.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
