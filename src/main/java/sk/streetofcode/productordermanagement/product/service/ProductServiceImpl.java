package sk.streetofcode.productordermanagement.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.exception.BadRequestException;
import sk.streetofcode.productordermanagement.product.api.dto.AmountDTO;
import sk.streetofcode.productordermanagement.product.api.dto.ProductDTO;
import sk.streetofcode.productordermanagement.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.product.entity.ProductEntity;
import sk.streetofcode.productordermanagement.product.repository.ProductRepository;
import sk.streetofcode.productordermanagement.product.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.product.api.request.UpdateProductRequest;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    // Using ModelMapper for entity-DTO conversion
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    private ProductEntity getProductEntityById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public void decreaseProductAmount(long id, long amount) {
        ProductEntity product = this.getProductEntityById(id);
        if (product.getAmount() < amount) {
            throw new BadRequestException("Not enough product in stock");
        }

        product.setAmount(product.getAmount() - amount);
        productRepository.save(product);
    }

    @Override
    public ProductDTO getProductById(long id) {
        ProductEntity productEntity = this.getProductEntityById(id);

        return this.modelMapper.map(productEntity, ProductDTO.class);
    }

    @Override
    public Iterable<ProductDTO> getAllProducts() {
        List<ProductEntity> products = this.productRepository.findAll();

        return products.stream()
                    .map(productEntity -> this.modelMapper.map(productEntity, ProductDTO.class))
                    .toList();

    }

    @Override
    public AmountDTO getProductAmount(long id) {
        ProductEntity product = this.getProductEntityById(id);
        return this.modelMapper.map(product, AmountDTO.class);
    }

    public ProductDTO addProduct(AddProductRequest request) {
        final ProductEntity product = new ProductEntity(
                request.getName(),
                request.getDescription(),
                request.getAmount(),
                request.getPrice()
        );

        ProductEntity savedProduct = productRepository.save(product);
        return this.modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public AmountDTO addProductAmount(long id, long amount) {
        ProductEntity product = this.getProductEntityById(id);
        product.setAmount(product.getAmount() + amount);

        ProductEntity savedProductEntity = productRepository.save(product);
        return new AmountDTO(savedProductEntity.getAmount());
    }

    @Override
    public ProductDTO updateProduct(long id, UpdateProductRequest request) {
        ProductEntity product = this.getProductEntityById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());

        ProductEntity savedProduct = productRepository.save(product);
        return this.modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public void deleteProduct(long id) {
        ProductEntity product = this.getProductEntityById(id);

        productRepository.delete(product);
    }
}
