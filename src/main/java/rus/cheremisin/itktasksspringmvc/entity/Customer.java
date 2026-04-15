package rus.cheremisin.itktasksspringmvc.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Setter;
import rus.cheremisin.itktasksspringmvc.config.MyViews;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "customers")
@Data
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id", nullable = false)
    @JsonView(MyViews.CustomerDetails.class)
    private Long id;

    @JsonView(MyViews.CustomerSummary.class)
    private String firstName;

    @JsonView(MyViews.CustomerSummary.class)
    private String lastName;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Incorrect email format"
    )
    @JsonView(MyViews.CustomerSummary.class)
    private String email;

    @Pattern(
            regexp = "^\\+7\\s9\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}$",
            message = "Incorrect phone number format. Use '+7 9хх ххх хх хх' (with SPACES!)"
    )
    private String contactNumber;

//    @OneToMany(mappedBy = "customer")
//    @JsonView(MyViews.CustomerDetails.class)
//    private List<Order> orders = new ArrayList<>();

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer(Long id, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer() {
    }


}
