package sk.streetofcode.productordermanagement.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return null;
    }

    @Override
    public int getProductAmount(Long id) {
        return 0;
    }

    public Product addProduct(ProductRequestDTO request) {
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
    public Product updateProduct(Long id, ProductRequestDTO request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
