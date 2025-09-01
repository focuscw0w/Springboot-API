package sk.streetofcode.productordermanagement.product;

public interface IProductService {
    // GET
    public Product getProductById(Long id);
    public Iterable<Product> getAllProducts();
    public int getProductAmount(Long id);

    // POST
    public Product addProduct(ProductRequestDTO request);
    public int addProductAmount(Long id, int amount);

    // PUT
    public Product updateProduct(Long id, ProductRequestDTO request);

    // DELETE
    public void deleteProduct(Long id);
}
