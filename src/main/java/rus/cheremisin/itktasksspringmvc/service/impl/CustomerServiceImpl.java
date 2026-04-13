package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.CustomerDAO;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.entity.ShoppingOrder;
import rus.cheremisin.itktasksspringmvc.exception.NoCustomerFoundException;
import rus.cheremisin.itktasksspringmvc.exception.OrderListIsNullException;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerDAO.findById(id);
        if (customerOptional.isEmpty()) {
            throw new NoCustomerFoundException("no customer with such ID");
        }
        return customerOptional.get();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        if (customer != null) {
            return customerDAO.save(customer);
        } else {
            throw new NullPointerException("cannot add null customer");
        }
    }

    @Override
    public Customer editCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerDAO.findById(id);
        if (customerOptional.isEmpty()) {
            throw new NoCustomerFoundException("no customer with such ID");
        }
        Customer existingCustomer = customerOptional.get();
        if (updatedCustomer != null) {
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            List<ShoppingOrder> updatedShoppingOrders = updatedCustomer.getOrders();
            if (updatedShoppingOrders != null) {
                existingCustomer.getOrders().clear();
                existingCustomer.getOrders().addAll(updatedCustomer.getOrders());
            } else {
                throw new OrderListIsNullException("Order list is null!");
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
            throw new NoSuchElementException("no customer with such ID");
        }
        customerDAO.delete(customerOptional.get());
    }
}
