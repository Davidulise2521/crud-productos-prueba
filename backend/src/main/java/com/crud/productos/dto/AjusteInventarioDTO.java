package com.crud.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AjusteInventarioDTO {
    @NotNull(message = "El ajuste es obligatorio")
    private Integer ajuste;

    @NotBlank(message = "La justificación es obligatoria")
    private String justificacion;
}
