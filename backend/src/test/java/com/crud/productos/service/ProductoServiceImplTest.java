package com.crud.productos.service;

import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import com.crud.productos.entity.Producto;
import com.crud.productos.exception.BadRequestException;
import com.crud.productos.exception.ResourceNotFoundException;
import com.crud.productos.mapper.ProductoMapper;
import com.crud.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private ProductoMapper mapper;

    @InjectMocks
    private ProductoServiceImpl service;

    @Test
    void testCrearProducto_Exito() {
        ProductoCreacionDTO dto = new ProductoCreacionDTO();
        dto.setNombre("Telefono");
        dto.setPrecio(100.0);
        dto.setExistencias(10);

        Producto entity = new Producto();
        ProductoDTO responseDto = new ProductoDTO();

        when(repository.existsByNombre(dto.getNombre())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDto);

        ProductoDTO result = service.crearProducto(dto);
        assertNotNull(result);
    }

    @Test
    void testCrearProducto_NombreDuplicado() {
        ProductoCreacionDTO dto = new ProductoCreacionDTO();
        dto.setNombre("Telefono");

        when(repository.existsByNombre(dto.getNombre())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> service.crearProducto(dto));
    }
}
