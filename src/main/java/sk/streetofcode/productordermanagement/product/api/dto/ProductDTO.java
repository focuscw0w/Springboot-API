package sk.streetofcode.productordermanagement.product.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    long id;
    String name;
    String description;
    long amount;
    double price;
}
