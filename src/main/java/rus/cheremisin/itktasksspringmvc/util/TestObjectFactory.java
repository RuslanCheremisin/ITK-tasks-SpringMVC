package rus.cheremisin.itktasksspringmvc.util;

import lombok.Getter;
import org.springframework.data.domain.*;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

import java.util.List;

@Getter
public class TestObjectFactory {
    private Long validId = 1L;
    private Long invalidId = 999L;
    private Customer validCustomer = new Customer("Ivan", "Ivanov", "ivan@ivanov.net");
    private Customer validCustomer2 = new Customer("Stepan", "Stepanov", "stepan@stepanov.net");
    private Customer validCustomerWithGeneratedId = new Customer(1L,"Ivan", "Ivanov", "ivan@ivanov.net");
    private Customer validCustomerWithGeneratedId2 = new Customer(2L,"Stepan", "Stepanov", "stepan@stepanov.net");
    private List<Customer> customerList = List.of(validCustomerWithGeneratedId, validCustomerWithGeneratedId2);
    private Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
    private Page<Customer> customerPage = new PageImpl<>(customerList, pageable, 100);
}
