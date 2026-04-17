package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.CustomerRepository;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("pageable is null!");
        }
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("no customer with such ID");
        }
        return customerOptional.get();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("cannot add null customer");
        }
//        if (customer.getOrders() == null) {
//            throw new ShoppingOrderListIsNullException("Order list is null!");
//        }
        return customerRepository.save(customer);
    }


    @Override
    public Customer editCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("no customer with such ID");
        }
        Customer existingCustomer = customerOptional.get();
        if (updatedCustomer != null) {
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
//            List<Order> updatedOrders = updatedCustomer.getOrders();
//            if (updatedOrders != null) {
//                existingCustomer.setOrders(updatedCustomer.getOrders());
//            } else {
//                throw new ShoppingOrderListIsNullException("Order list is null!");
//            }
            return customerRepository.save(existingCustomer);
        } else {
            throw new NullPointerException("cannot update from null customer");
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("no customer with such ID");
        }
        customerRepository.delete(customerOptional.get());
    }
}
