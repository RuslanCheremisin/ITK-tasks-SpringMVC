package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

public interface CustomerService {
    Customer getCustomerById(Long id);

    Customer addCustomer(Customer customer);

    Customer editCustomer(Long id, Customer customer);

    void deleteCustomerById(Long id);

    Page<Customer> getAllCustomers(Pageable pageable);
}
