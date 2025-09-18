package hu.shopix.main.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.shopix.main.dto.ProductResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.ProductMapper;
import hu.shopix.main.model.Product;
import hu.shopix.main.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final ProductMapper mapper;
	
	@Transactional(readOnly = true) // Optimalizálás
	public Page<ProductResponse> getAllProducts(Pageable pageable){
		return productRepository.findAll(pageable).map(mapper::toResponse);
	}
	
	@Transactional(readOnly = true)
	public ProductResponse getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Termék", id));
		return mapper.toResponse(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductResponse> getProductByName(String name, Pageable pageable){
		return productRepository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toResponse);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductResponse> getProductByActiveTrue(Pageable pageable){
		return productRepository.findByActiveTrue(pageable).map(mapper::toResponse);
	}
	
	@Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).map(mapper::toResponse);
    }
}
