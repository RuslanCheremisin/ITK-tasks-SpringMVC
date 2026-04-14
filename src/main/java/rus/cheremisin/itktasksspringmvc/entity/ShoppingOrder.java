package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.*;
import rus.cheremisin.itktasksspringmvc.enums.ShoppingOrderStatus;

import java.math.BigInteger;

@Entity
public class ShoppingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String longStringOfProducts;
    private BigInteger totalSum;
    @Enumerated
    private ShoppingOrderStatus shoppingOrderStatus;
    @ManyToOne(targetEntity = Customer.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Customer customer;

    public ShoppingOrder(String longStringOfProducts, BigInteger totalSum, ShoppingOrderStatus shoppingOrderStatus, Customer customer) {
        this.longStringOfProducts = longStringOfProducts;
        this.totalSum = totalSum;
        this.shoppingOrderStatus = shoppingOrderStatus;
        this.customer = customer;
    }

    public ShoppingOrder() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShoppingOrderStatus getShoppingOrderStatus() {
        return shoppingOrderStatus;
    }

    public void setShoppingOrderStatus(ShoppingOrderStatus shoppingOrderStatus) {
        this.shoppingOrderStatus = shoppingOrderStatus;
    }

    public BigInteger getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigInteger totalSum) {
        this.totalSum = totalSum;
    }

    public String getLongStringOfProducts() {
        return longStringOfProducts;
    }

    public void setLongStringOfProducts(String longStringOfProducts) {
        this.longStringOfProducts = longStringOfProducts;
    }
}
