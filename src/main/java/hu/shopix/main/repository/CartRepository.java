package hu.shopix.main.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.shopix.main.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findByUserId(Long userId, Pageable pageable);
    Optional<Cart> findFirstByUserIdAndStatus(Long userId, String status);
}
