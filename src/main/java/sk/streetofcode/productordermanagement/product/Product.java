package sk.streetofcode.productordermanagement.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int amount;
    private double price;

    public Product(String name, String description, int amount, double price) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }

}
