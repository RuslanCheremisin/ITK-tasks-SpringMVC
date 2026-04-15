package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import rus.cheremisin.itktasksspringmvc.enums.ShoppingOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private List<Product> products;

    private LocalDate orderDate;

    private String shippingAddress;

    private BigDecimal totalPrice;

    @Enumerated
    private ShoppingOrderStatus orderStatus;

    public Order(Customer customer,
                 List<Product> products,
                 LocalDate orderDate,
                 String shippingAddress,
                 BigDecimal totalPrice,
                 ShoppingOrderStatus orderStatus) {
        this.customer = customer;
        this.products = products;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }


}
