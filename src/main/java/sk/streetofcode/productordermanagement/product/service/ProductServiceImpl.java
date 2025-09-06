package sk.streetofcode.productordermanagement.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.product.api.dto.AmountDTO;
import sk.streetofcode.productordermanagement.product.api.dto.ProductDTO;
import sk.streetofcode.productordermanagement.product.api.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.product.api.exception.ResourceNotFoundException;
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

    public ProductEntity getProductEntityById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public ProductDTO getProductById(long id) {
        ProductEntity productEntity = this.getProductEntityById(id);

        return this.modelMapper.map(productEntity, ProductDTO.class);
    }

    @Override
    public Iterable<ProductDTO> getAllProducts() {
        try {
            List<ProductEntity> productEntities = this.productRepository.findAll();

            return productEntities.stream()
                    .map(productEntity -> this.modelMapper.map(productEntity, ProductDTO.class))
                    .toList();
        } catch (Exception e) {
            throw new InternalErrorException("Failed to retrieve products " + e);
        }
    }

    @Override
    public AmountDTO getProductAmount(long id) {
        ProductEntity productEntity = this.getProductEntityById(id);
        return this.modelMapper.map(productEntity, AmountDTO.class);
    }

    public ProductDTO addProduct(AddProductRequest request) {
        final ProductEntity product = new ProductEntity(
                request.getName(),
                request.getDescription(),
                request.getAmount(),
                request.getPrice()
        );

        try {
            ProductEntity savedProductEntity = productRepository.save(product);
            return this.modelMapper.map(savedProductEntity, ProductDTO.class);
        } catch (Exception e) {
            throw new InternalErrorException("Failed to add product " + e);
        }
    }

    @Override
    public AmountDTO addProductAmount(long id, long amount) {
        ProductEntity productEntity = this.getProductEntityById(id);
        productEntity.setAmount(productEntity.getAmount() + amount);

        try {
            ProductEntity savedProductEntity = productRepository.save(productEntity);
            return new AmountDTO(savedProductEntity.getAmount());
        } catch (Exception e) {
            throw new InternalErrorException("Failed to add product amount " + e);
        }
    }

    @Override
    public ProductDTO updateProduct(long id, UpdateProductRequest request) {
        ProductEntity productEntity = this.getProductEntityById(id);

        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());

        try {
            ProductEntity savedProductEntity = productRepository.save(productEntity);
            return this.modelMapper.map(savedProductEntity, ProductDTO.class);
        } catch (Exception e) {
            throw new InternalErrorException("Failed to update product " + e);
        }
    }

    @Override
    public void deleteProduct(long id) {
        ProductEntity product = this.getProductEntityById(id);

        try {
            productRepository.delete(product);
        } catch (Exception e) {
            throw new InternalErrorException("Failed to delete product " + e);
        }
    }
}
