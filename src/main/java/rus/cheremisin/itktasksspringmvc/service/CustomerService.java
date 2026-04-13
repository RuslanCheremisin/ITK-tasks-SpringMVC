package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.CustomerDAO;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomerById(Long id);
    Customer addCustomer(Customer customer);
    Customer editCustomer(Long id, Customer customer);
    void deleteCustomerById(Long id);

    Page<Customer> getAllCustomers(Pageable pageable);
}
