package sk.streetofcode.productordermanagement.product.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAmountRequest {
    private int amount;
}
