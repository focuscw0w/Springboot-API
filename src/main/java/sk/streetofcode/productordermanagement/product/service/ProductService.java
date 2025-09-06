package sk.streetofcode.productordermanagement.product.service;

import sk.streetofcode.productordermanagement.product.api.dto.AmountDTO;
import sk.streetofcode.productordermanagement.product.api.dto.ProductDTO;
import sk.streetofcode.productordermanagement.product.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.api.request.UpdateProductRequest;

public interface ProductService {
    // GET
    ProductDTO getProductById(long id);
    Iterable<ProductDTO> getAllProducts();
    AmountDTO getProductAmount(long id);

    // POST
    ProductDTO addProduct(AddProductRequest request);
    AmountDTO addProductAmount(long id, long amount);

    // PUT
    ProductDTO updateProduct(long id, UpdateProductRequest request);

    // DELETE
    void deleteProduct(long id);
}
