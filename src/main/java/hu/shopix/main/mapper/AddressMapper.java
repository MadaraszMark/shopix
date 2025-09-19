package hu.shopix.main.mapper;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.AddressRequest;
import hu.shopix.main.dto.AddressResponse;
import hu.shopix.main.model.Address;

@Component
public class AddressMapper {

    public AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .createdAt(address.getCreatedAt())
                .build();
    }

    public Address toEntity(AddressRequest request) {
        return Address.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .build();
    }

    public void updateEntity(Address address, AddressRequest request) {
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
    }
}

