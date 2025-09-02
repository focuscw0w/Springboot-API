package sk.streetofcode.productordermanagement.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    ResponseEntity<Product> addProduct(@RequestBody ProductRequestDTO request) {
       return ResponseEntity.ok(productService.addProduct(request));
    }
}
