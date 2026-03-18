package com.crud.productos.integration;

import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import com.crud.productos.entity.Producto;
import com.crud.productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testFlujoCompleto() {
        ProductoCreacionDTO dto = new ProductoCreacionDTO();
        dto.setNombre("Integracion");
        dto.setPrecio(200.0);
        dto.setExistencias(5);

        ResponseEntity<ProductoDTO> response = restTemplate.postForEntity("/productos", dto, ProductoDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Integracion", response.getBody().getNombre());
    }
}
