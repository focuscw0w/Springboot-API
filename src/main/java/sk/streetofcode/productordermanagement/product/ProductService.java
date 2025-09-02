package sk.streetofcode.productordermanagement.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.streetofcode.productordermanagement.product.requests.AddProductRequest;
import sk.streetofcode.productordermanagement.product.requests.EditProductRequest;

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
    public int getProductAmount(Long id) {
        return 0;
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
    public int addProductAmount(Long id, int amount) {
        return 0;
    }

    @Override
    public Product updateProduct(Long id, EditProductRequest request) {
        Product existingProduct = this.getProductById(id);

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
