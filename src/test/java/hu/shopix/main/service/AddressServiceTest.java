package hu.shopix.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.AddressRequest;
import hu.shopix.main.dto.AddressResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.AddressMapper;
import hu.shopix.main.model.Address;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.AddressRepository;
import hu.shopix.main.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressMapper mapper;

    @InjectMocks
    private AddressService addressService;

    private User user;
    private Address address;
    private AddressRequest request;
    private AddressResponse response;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).email("test@test.com").build();
        address = Address.builder().id(10L).street("Test utca 1").user(user).build();
        request = AddressRequest.builder().street("Új utca 2").build();
        response = AddressResponse.builder().id(10L).street("Új utca 2").build();
    }

    @Test
    void testListMyAddresses() {
        PageRequest pageable = PageRequest.of(0, 5);
        when(addressRepository.findByUserId(1L, pageable)).thenReturn(new PageImpl<>(List.of(address)));
        when(mapper.toResponse(address)).thenReturn(response);

        Page<AddressResponse> result = addressService.listMy(1L, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Új utca 2", result.getContent().get(0).getStreet());
        verify(addressRepository).findByUserId(1L, pageable);
    }

    @Test
    void testCreateAddress() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toEntity(request)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(mapper.toResponse(address)).thenReturn(response);

        AddressResponse result = addressService.create(1L, request);

        assertNotNull(result);
        assertEquals("Új utca 2", result.getStreet());
        verify(addressRepository).save(address);
    }

    @Test
    void testCreateAddress_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.create(1L, request));
    }

    @Test
    void testUpdateAddress_Success() {
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));
        when(mapper.toResponse(address)).thenReturn(response);
        when(addressRepository.save(address)).thenReturn(address);

        AddressResponse result = addressService.update(1L, 10L, request);

        assertNotNull(result);
        verify(mapper).updateEntity(address, request);
        verify(addressRepository).save(address);
    }

    @Test
    void testUpdateAddress_NotOwner() {
        User otherUser = User.builder().id(2L).build();
        address.setUser(otherUser);
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));

        assertThrows(ResponseStatusException.class, () -> addressService.update(1L, 10L, request));
    }

    @Test
    void testUpdateAddress_NotFound() {
        when(addressRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.update(1L, 10L, request));
    }

    @Test
    void testDeleteAddress_Success() {
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));

        addressService.delete(1L, 10L);

        verify(addressRepository).delete(address);
    }

    @Test
    void testDeleteAddress_NotOwner() {
        User otherUser = User.builder().id(2L).build();
        address.setUser(otherUser);
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));

        assertThrows(ResponseStatusException.class, () -> addressService.delete(1L, 10L));
    }

    @Test
    void testDeleteAddress_NotFound() {
        when(addressRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.delete(1L, 10L));
    }
}
