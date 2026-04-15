package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
