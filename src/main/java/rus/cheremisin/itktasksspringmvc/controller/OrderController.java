package rus.cheremisin.itktasksspringmvc.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.entity.Order;
import rus.cheremisin.itktasksspringmvc.entity.Product;
import rus.cheremisin.itktasksspringmvc.service.OrderService;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.util.List;

@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Qualifier("orderMapper")
    private final RequestMapper<Order> requestMapper;

    @Autowired
    public OrderController(OrderService orderService, RequestMapper<Order> requestMapper) {
        this.orderService = orderService;
        this.requestMapper = requestMapper;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> customerPage = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(customerPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getOrderById(@PathVariable("id") @Min(1) Long id) {
        Order order = orderService.getOrderById(id);
        byte[] json = requestMapper.toJsonFile(order);
        StringBuilder sb = new StringBuilder("attachment; filename=order_")
                .append(order.getOrderId())
                .append("_customer_")
                .append(order.getCustomer().getId())
                .append(".json");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, sb.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Order> addOrder(@RequestParam("file") MultipartFile orderFile) {
        Order order = requestMapper.toEntity(orderFile);
        return ResponseEntity.ok(orderService.addOrder(order));
    }


}
