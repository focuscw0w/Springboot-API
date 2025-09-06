package sk.streetofcode.productordermanagement.product.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private String name;
    private String description;
    private int amount;
    private double price;
}
