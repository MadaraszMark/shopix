package hu.shopix.main.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.AddressRequest;
import hu.shopix.main.dto.AddressResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.AddressMapper;
import hu.shopix.main.model.Address;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.AddressRepository;
import hu.shopix.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    @Transactional(readOnly = true) // Optimalizálás
    public Page<AddressResponse> listMy(Long userId, Pageable pageable) {
        return addressRepository.findByUserId(userId, pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    public AddressResponse create(Long userId, AddressRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Felhasználó", userId));

        Address address = mapper.toEntity(request);
        address.setUser(user);

        Address saved = addressRepository.save(address);
        return mapper.toResponse(saved);
    }

    @Transactional
    public AddressResponse update(Long userId, Long addressId, AddressRequest request) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Cím", addressId));

        if (!address.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nem a te címed.");
        }

        mapper.updateEntity(address, request);
        Address saved = addressRepository.save(address);
        return mapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Cím", addressId));

        if (!address.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nem a te címed.");
        }

        addressRepository.delete(address);
    }
}

