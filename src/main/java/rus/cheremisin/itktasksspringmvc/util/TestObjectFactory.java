package rus.cheremisin.itktasksspringmvc.util;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

import java.util.List;

@Getter
public class TestObjectFactory {
    private final Long validId = 1L;
    private final Long invalidId = 999L;
    private final Customer validCustomer = new Customer("Ivan", "Ivanov", "ivan@ivanov.net", "+7 999 123 45 67");
    private final Customer validCustomer2 = new Customer("Stepan", "Stepanov", "stepan@stepanov.net", "+7 935 098 76 54");
    private final Customer validCustomerWithGeneratedId = new Customer(1L,"Ivan", "Ivanov", "ivan@ivanov.net", "+7 999 123 45 67");
    private final Customer validCustomerWithGeneratedId2 = new Customer(2L,"Stepan", "Stepanov", "stepan@stepanov.net", "+7 935 098 76 54");
    private final List<Customer> customerList = List.of(validCustomer, validCustomer2);
    private final List<Customer> customerListWithId = List.of(validCustomerWithGeneratedId, validCustomerWithGeneratedId2);
    private final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
    private final Page<Customer> customerPage = new PageImpl<>(customerList, pageable, 100);
}
