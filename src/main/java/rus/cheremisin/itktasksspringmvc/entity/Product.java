package rus.cheremisin.itktasksspringmvc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "products")
@Data
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;

    public Product(Long productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    public Product() {
    }

}
