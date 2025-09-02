package sk.streetofcode.productordermanagement.product;

import sk.streetofcode.productordermanagement.product.requests.AddProductRequest;
import sk.streetofcode.productordermanagement.product.requests.EditProductRequest;

import java.util.Optional;

public interface IProductService {
    // GET
    public Product getProductById(long id);
    public Iterable<Product> getAllProducts();
    public int getProductAmount(Long id);

    // POST
    public Product addProduct(AddProductRequest request);
    public int addProductAmount(Long id, int amount);

    // PUT
    public Product updateProduct(Long id, EditProductRequest request);

    // DELETE
    public void deleteProduct(Long id);
}
