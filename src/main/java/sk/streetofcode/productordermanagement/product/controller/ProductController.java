package sk.streetofcode.productordermanagement.product.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/product")
    ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductRequest request) {
       return ResponseEntity.status(HttpStatus.CREATED).
               body(productService.addProduct(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product amount updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/product/{id}/amount")
    ResponseEntity<AmountDTO> addProductAmount(@PathVariable long id, @RequestBody ProductAmountRequest request) {
        return ResponseEntity.ok(productService.addProductAmount(id, request.getAmount()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    @GetMapping("/product/{id}")
    ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/product")
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok((List<ProductDTO>) productService.getAllProducts());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product amount retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    @GetMapping("/product/{id}/amount")
    ResponseEntity<AmountDTO> getProductAmount(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductAmount(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/product/{id}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable long id, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/product/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
