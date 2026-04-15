package rus.cheremisin.itktasksspringmvc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.config.MyViews;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.util.List;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Qualifier("customerMapper")
    private final RequestMapper<Customer> requestMapper;

    @Autowired
    public CustomerController(CustomerService customerService, RequestMapper<Customer> requestMapper) {
        this.customerService = customerService;
        this.requestMapper = requestMapper;
    }

    @GetMapping
    @JsonView(MyViews.CustomerSummary.class)
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
    public ResponseEntity<byte[]> getCustomerById(@PathVariable("id") @Min(1) Long id) {
        Customer customer = customerService.getCustomerById(id);
        byte[] json = requestMapper.toJsonFile(customer);
        StringBuilder sb = new StringBuilder("attachment; filename=customer_")
                .append(customer.getId())
                .append(".json");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, sb.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @JsonView(MyViews.CustomerSummary.class)
    public ResponseEntity<Customer> addCustomer(@RequestParam("file") MultipartFile customerFile) {
        Customer customer = requestMapper.toEntity(customerFile);
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @PutMapping("/{id}")
    @JsonView(MyViews.CustomerSummary.class)
    public ResponseEntity<Customer> editCustomer(@PathVariable("id") @Min(1) Long id,
                                                 @Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.editCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") @Min(1) Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }


}
