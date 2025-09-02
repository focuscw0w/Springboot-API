package sk.streetofcode.productordermanagement.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sk.streetofcode.productordermanagement.product.requests.AddProductRequest;
import sk.streetofcode.productordermanagement.product.requests.EditProductRequest;

import java.util.List;

@Slf4j
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    ResponseEntity<Product> addProduct(@RequestBody AddProductRequest request) {
       return ResponseEntity.status(HttpStatus.CREATED).
               body(productService.addProduct(request));
    }

    @GetMapping("/product/{id}")
    ResponseEntity<Product> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product")
    ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok((List<Product>) productService.getAllProducts());
    }

    @PutMapping("/product/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody EditProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
}
