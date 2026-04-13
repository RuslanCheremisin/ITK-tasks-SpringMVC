package rus.cheremisin.itktasksspringmvc.util;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import rus.cheremisin.itktasksspringmvc.DTO.MyPageResponse;
import rus.cheremisin.itktasksspringmvc.entity.Customer;

@Component
public class PageResponseMapper {
    public MyPageResponse<Customer> mapCustomerPageResponse(Page<Customer> page) {
        return new MyPageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
