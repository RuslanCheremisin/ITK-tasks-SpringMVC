package rus.cheremisin.itktasksspringmvc.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import rus.cheremisin.itktasksspringmvc.config.MyViews;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JsonView(MyViews.UserDetails.class)
    private Long id;

    @JsonView(MyViews.UserSummary.class)
    private String firstName;

    @JsonView(MyViews.UserSummary.class)
    private String lastName;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Incorrect email format"
    )
    @JsonView(MyViews.UserSummary.class)
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonView(MyViews.UserDetails.class)
    private List<ShoppingOrder> shoppingOrders = new ArrayList<>();

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public List<ShoppingOrder> getShoppingOrders() {
        return shoppingOrders;
    }

    public void setShoppingOrders(List<ShoppingOrder> shoppingOrders) {
        this.shoppingOrders = shoppingOrders;
    }
}
