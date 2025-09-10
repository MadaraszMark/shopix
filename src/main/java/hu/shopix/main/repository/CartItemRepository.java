package hu.shopix.main.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.shopix.main.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	Page<CartItem> findByCartId(Long cartId, Pageable pageable);
	Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);	
}

