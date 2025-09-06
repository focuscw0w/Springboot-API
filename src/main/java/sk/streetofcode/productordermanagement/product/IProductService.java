package sk.streetofcode.productordermanagement.product;

import sk.streetofcode.productordermanagement.product.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.request.EditProductRequest;

public interface IProductService {
    // GET
    public Product getProductById(long id);
    public Iterable<Product> getAllProducts();
    public Amount getProductAmount(long id);

    // POST
    public Product addProduct(AddProductRequest request);
    public long addProductAmount(long id, long amount);

    // PUT
    public Product updateProduct(long id, EditProductRequest request);

    // DELETE
    public void deleteProduct(long id);
}
