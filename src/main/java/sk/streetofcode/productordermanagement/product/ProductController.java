package sk.streetofcode.productordermanagement.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.product.request.AddProductAmountRequest;
import sk.streetofcode.productordermanagement.product.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.request.EditProductRequest;

import java.util.List;

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

    @PostMapping("/product/{id}/amount")
    ResponseEntity<Amount> addProductAmount(@PathVariable long id, @RequestBody AddProductAmountRequest request) {
        return ResponseEntity.ok(new Amount(productService.addProductAmount(id, request.getAmount())));
    }

    @GetMapping("/product/{id}")
    ResponseEntity<Product> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product")
    ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok((List<Product>) productService.getAllProducts());
    }

    @GetMapping("/product/{id}/amount")
    ResponseEntity<Amount> getProductAmount(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductAmount(id));
    }

    @PutMapping("/product/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody EditProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/product/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
