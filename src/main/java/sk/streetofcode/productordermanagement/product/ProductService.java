package sk.streetofcode.productordermanagement.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.streetofcode.productordermanagement.product.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.request.EditProductRequest;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Amount getProductAmount(long id) {
        Product product = this.getProductById(id);
        return new Amount(product.getAmount());
    }

    public Product addProduct(AddProductRequest request) {
        final Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getAmount(),
                request.getPrice()
        );

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
    }

    @Override
    public long addProductAmount(long id, long amount) {
        Product product = this.getProductById(id);
        product.setAmount(product.getAmount() + amount);

        productRepository.save(product);

        return product.getAmount();
    }

    @Override
    public Product updateProduct(long id, EditProductRequest request) {
        Product product = this.getProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        Product product = this.getProductById(id);
        productRepository.delete(product);
    }
}
