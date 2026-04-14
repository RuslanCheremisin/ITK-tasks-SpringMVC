package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.CustomerDAO;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.entity.ShoppingOrder;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.exception.ShoppingOrderListIsNullException;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("pageable is null!");
        }
        return customerDAO.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerDAO.findById(id);
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
        if (customer.getShoppingOrders() == null) {
            throw new ShoppingOrderListIsNullException("Order list is null!");
        }
        return customerDAO.save(customer);
    }


    @Override
    public Customer editCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerDAO.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("no customer with such ID");
        }
        Customer existingCustomer = customerOptional.get();
        if (updatedCustomer != null) {
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            List<ShoppingOrder> updatedShoppingOrders = updatedCustomer.getShoppingOrders();
            if (updatedShoppingOrders != null) {
                existingCustomer.setShoppingOrders(updatedCustomer.getShoppingOrders());
            } else {
                throw new ShoppingOrderListIsNullException("Order list is null!");
            }
            return customerDAO.save(existingCustomer);
        } else {
            throw new NullPointerException("cannot update from null customer");
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = customerDAO.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("no customer with such ID");
        }
        customerDAO.delete(customerOptional.get());
    }
}
