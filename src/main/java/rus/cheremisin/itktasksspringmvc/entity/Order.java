package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import rus.cheremisin.itktasksspringmvc.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;


    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    @OneToMany
    private List<Product> products = new ArrayList<>();

    private LocalDate orderDate;

    private String shippingAddress;

    private BigDecimal totalPrice;

    @Enumerated
    private OrderStatus orderStatus;

    public Order(Customer customer,
                 LocalDate orderDate,
                 String shippingAddress,
                 BigDecimal totalPrice,
                 OrderStatus orderStatus) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }


}
