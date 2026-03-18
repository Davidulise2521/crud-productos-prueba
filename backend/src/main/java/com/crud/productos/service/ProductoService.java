package com.crud.productos.service;

import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<ProductoDTO> listarProductos(Pageable pageable);
    ProductoDTO crearProducto(ProductoCreacionDTO dto);
    ProductoDTO obtenerProducto(Long id);
    ProductoDTO actualizarProducto(Long id, ProductoCreacionDTO dto);
    ProductoDTO cambiarEstadoActivo(Long id, boolean activo);
    ProductoDTO ajustarInventario(Long id, Integer ajuste, String justificacion);
}
