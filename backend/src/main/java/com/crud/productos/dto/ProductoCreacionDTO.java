package com.crud.productos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoCreacionDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String marca;

    private String categoria;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "Las existencias son obligatorias")
    @Min(value = 0, message = "Las existencias deben ser mayores o iguales a 0")
    private Integer existencias;
}
