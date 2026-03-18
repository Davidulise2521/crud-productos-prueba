package com.crud.productos.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String categoria;
    private Double precio;
    private Integer existencias;
    private Boolean activo;
}
