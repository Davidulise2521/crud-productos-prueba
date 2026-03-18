package com.crud.productos.controller;

import com.crud.productos.dto.AjusteInventarioDTO;
import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import com.crud.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de Productos.
 * <p>
 * Su única responsabilidad es exponer los Endpoints HTTP,
 * capturar DTOs, validarlos mediante Validation (@Valid)
 * y retornarlos con sus status correspondientes, delegando
 * toda la lógica de negocio imperativa a {@link ProductoService}.
 * 
 * @author Pacheco Ramírez David Ulises
 * @version 1.1
 */
@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> listarProductos(Pageable pageable) {
        return ResponseEntity.ok(service.listarProductos(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoCreacionDTO dto) {
        return new ResponseEntity<>(service.crearProducto(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerProducto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoCreacionDTO dto) {
        return ResponseEntity.ok(service.actualizarProducto(id, dto));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ProductoDTO> cambiarEstadoActivo(
            @PathVariable Long id,
            @RequestParam boolean activo) {
        return ResponseEntity.ok(service.cambiarEstadoActivo(id, activo));
    }

    @PostMapping("/{id}/ajustar")
    public ResponseEntity<ProductoDTO> ajustarInventario(
            @PathVariable Long id,
            @Valid @RequestBody AjusteInventarioDTO dto) {
        return ResponseEntity.ok(service.ajustarInventario(id, dto.getAjuste(), dto.getJustificacion()));
    }
}
