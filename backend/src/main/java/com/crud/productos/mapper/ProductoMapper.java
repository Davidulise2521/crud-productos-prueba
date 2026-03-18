package com.crud.productos.mapper;

import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import com.crud.productos.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setMarca(producto.getMarca());
        dto.setCategoria(producto.getCategoria());
        dto.setPrecio(producto.getPrecio());
        dto.setExistencias(producto.getExistencias());
        dto.setActivo(producto.getActivo());
        return dto;
    }

    public Producto toEntity(ProductoCreacionDTO dto) {
        if (dto == null) return null;
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setMarca(dto.getMarca());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setExistencias(dto.getExistencias());
        producto.setActivo(true); // Siempre activo por defecto al crear
        return producto;
    }
}
