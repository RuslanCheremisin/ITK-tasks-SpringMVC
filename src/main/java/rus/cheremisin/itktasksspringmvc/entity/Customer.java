package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "customer")
    private List<ShoppingOrder> shoppingOrders;

    public Customer(String firstName, String lastName, String email, List<ShoppingOrder> shoppingOrders) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.shoppingOrders = new ArrayList<>();
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ShoppingOrder> getOrders() {
        return shoppingOrders;
    }
}
