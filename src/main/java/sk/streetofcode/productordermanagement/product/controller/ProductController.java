package sk.streetofcode.productordermanagement.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.product.api.dto.AmountDTO;
import sk.streetofcode.productordermanagement.product.api.dto.ProductDTO;
import sk.streetofcode.productordermanagement.product.api.request.ProductAmountRequest;
import sk.streetofcode.productordermanagement.product.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.api.request.UpdateProductRequest;
import sk.streetofcode.productordermanagement.product.service.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductRequest request) {
       return ResponseEntity.status(HttpStatus.CREATED).
               body(productService.addProduct(request));
    }

    @PostMapping("/product/{id}/amount")
    ResponseEntity<AmountDTO> addProductAmount(@PathVariable long id, @RequestBody ProductAmountRequest request) {
        return ResponseEntity.ok(productService.addProductAmount(id, request.getAmount()));
    }

    @GetMapping("/product/{id}")
    ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product")
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok((List<ProductDTO>) productService.getAllProducts());
    }

    @GetMapping("/product/{id}/amount")
    ResponseEntity<AmountDTO> getProductAmount(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductAmount(id));
    }

    @PutMapping("/product/{id}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable long id, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/product/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
