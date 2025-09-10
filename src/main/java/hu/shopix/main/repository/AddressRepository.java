package hu.shopix.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.shopix.main.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	Page<Address> findByUserId(Long userId, Pageable pageable);
}
