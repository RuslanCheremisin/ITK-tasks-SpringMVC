package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.*;

@Entity
public class ShoppingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String longStringOfProducts;
    @ManyToOne(targetEntity = Customer.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Customer customer;

    public ShoppingOrder(String longStringOfProducts, Customer customer) {
        this.longStringOfProducts = longStringOfProducts;
        this.customer = customer;
    }

    public ShoppingOrder() {
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getLongStringOfProducts() {
        return longStringOfProducts;
    }

    public void setLongStringOfProducts(String longStringOfProducts) {
        this.longStringOfProducts = longStringOfProducts;
    }
}
