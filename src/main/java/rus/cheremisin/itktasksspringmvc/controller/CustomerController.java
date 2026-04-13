package rus.cheremisin.itktasksspringmvc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rus.cheremisin.itktasksspringmvc.config.MyViews;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @JsonView(MyViews.UserSummary.class)
    public ResponseEntity<List<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customerPage.getContent());
    }

    @GetMapping("/{id}")
    @JsonView(MyViews.UserDetails.class)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping()
    @JsonView(MyViews.UserSummary.class)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @PutMapping("/{id}")
    @JsonView(MyViews.UserSummary.class)
    public ResponseEntity<Customer> editCustomer(@PathVariable("id") @Min(1) Long id,
                                                 Customer customer) {
        return ResponseEntity.ok(customerService.editCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") @Min(1) Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }


}
