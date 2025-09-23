package hu.shopix.main.mapper;

import hu.shopix.main.dto.AddressRequest;
import hu.shopix.main.dto.AddressResponse;
import hu.shopix.main.model.Address;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private final AddressMapper mapper = new AddressMapper();

    @Test
    void testToEntity() {
        AddressRequest req = AddressRequest.builder()
                .street("Váci út 22.")
                .city("Budapest")
                .zipCode("1132")
                .country("Magyarország")
                .build();

        Address entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("Váci út 22.", entity.getStreet());
        assertEquals("Budapest", entity.getCity());
        assertEquals("1132", entity.getZipCode());
        assertEquals("Magyarország", entity.getCountry());
    }

    @Test
    void testToResponse() {
        LocalDateTime created = LocalDateTime.of(2025, 1, 1, 12, 0);
        Address entity = Address.builder()
                .id(42L)
                .street("Kossuth Lajos utca 1.")
                .city("Debrecen")
                .zipCode("4024")
                .country("Magyarország")
                .createdAt(created)
                .build();

        AddressResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(42L, resp.getId());
        assertEquals("Kossuth Lajos utca 1.", resp.getStreet());
        assertEquals("Debrecen", resp.getCity());
        assertEquals("4024", resp.getZipCode());
        assertEquals("Magyarország", resp.getCountry());
        assertEquals(created, resp.getCreatedAt());
    }

    @Test
    void testUpdateEntity() {
        Address entity = Address.builder()
                .id(10L)
                .street("Régi utca 5.")
                .city("Pécs")
                .zipCode("7621")
                .country("Magyarország")
                .build();

        AddressRequest update = AddressRequest.builder()
                .street("Új utca 10.")
                .city("Szeged")
                .zipCode("6720")
                .country("Magyarország")
                .build();

        mapper.updateEntity(entity, update);

        assertEquals(10L, entity.getId()); // nem változik
        assertEquals("Új utca 10.", entity.getStreet());
        assertEquals("Szeged", entity.getCity());
        assertEquals("6720", entity.getZipCode());
        assertEquals("Magyarország", entity.getCountry());
    }
}

