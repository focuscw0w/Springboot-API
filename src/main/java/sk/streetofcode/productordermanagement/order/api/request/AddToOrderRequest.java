package sk.streetofcode.productordermanagement.order.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToOrderRequest {
    private Long productId;
    private Long amount;
}
