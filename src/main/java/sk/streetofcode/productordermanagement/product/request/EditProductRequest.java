package sk.streetofcode.productordermanagement.product.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProductRequest {
    private String name;
    private String description;
}
