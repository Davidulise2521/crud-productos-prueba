package com.crud.productos.service;

import com.crud.productos.dto.ProductoCreacionDTO;
import com.crud.productos.dto.ProductoDTO;
import com.crud.productos.entity.Producto;
import com.crud.productos.exception.BadRequestException;
import com.crud.productos.exception.ResourceNotFoundException;
import com.crud.productos.mapper.ProductoMapper;
import com.crud.productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación principal del Servicio de Productos.
 * <p>
 * Actúa como el núcleo de la aplicación, ejecutando las reglas
 * de negocio de forma transaccional (@Transactional) aislando a la
 * base de datos subyacente de la capa web.
 * Aquí se validan condiciones complejas como prevención de
 * existencias negativas o la unicidad validada de un nombre de producto.
 * 
 * @author Pacheco Ramírez David Ulises
 */
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> listarProductos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional
    public ProductoDTO crearProducto(ProductoCreacionDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            throw new BadRequestException("Ya existe un producto con el mismo nombre.");
        }
        Producto producto = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(producto));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO obtenerProducto(Long id) {
        return mapper.toDTO(findById(id));
    }

    @Override
    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoCreacionDTO dto) {
        Producto producto = findById(id);
        if (repository.existsByNombreAndIdNot(dto.getNombre(), id)) {
            throw new BadRequestException("Ya existe otro producto con el mismo nombre.");
        }
        producto.setNombre(dto.getNombre());
        producto.setMarca(dto.getMarca());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setExistencias(dto.getExistencias());
        return mapper.toDTO(repository.save(producto));
    }

    @Override
    @Transactional
    public ProductoDTO cambiarEstadoActivo(Long id, boolean activo) {
        Producto producto = findById(id);
        producto.setActivo(activo);
        return mapper.toDTO(repository.save(producto));
    }

    @Override
    @Transactional
    public ProductoDTO ajustarInventario(Long id, Integer ajuste, String justificacion) {
        Producto producto = findById(id);
        if (justificacion == null || justificacion.trim().isEmpty()) {
            throw new BadRequestException("La justificación es obligatoria para ajustar el inventario.");
        }
        int nuevasExistencias = producto.getExistencias() + ajuste;
        if (nuevasExistencias < 0) {
            throw new BadRequestException("El ajuste resultaría en existencias negativas.");
        }
        producto.setExistencias(nuevasExistencias);
        return mapper.toDTO(repository.save(producto));
    }

    private Producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }
}
