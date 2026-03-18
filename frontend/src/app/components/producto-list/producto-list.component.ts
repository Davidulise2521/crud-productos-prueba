import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { Producto, AjusteInventario } from '../../models/producto';

declare var bootstrap: any;

/**
 * Componente principal para el Listado de Productos.
 * Funciona como un datatable que inyecta el `ProductoService` para
 * recabar métricas y listados paginados. Además de controlar
 * la apertura de modales para el ajuste de inventario y borrado lógico.
 */
@Component({
  selector: 'app-producto-list',
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent implements OnInit {
  productos: Producto[] = [];
  page = 0;
  size = 5;
  totalPages = 0;
  totalElements = 0;
  loading = false;
  error = '';

  productoSeleccionado: Producto | null = null;
  ajuste: number = 0;
  justificacion: string = '';
  ajusteModal: any;

  constructor(private productoService: ProductoService) { }

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos() {
    this.loading = true;
    this.productoService.listarProductos(this.page, this.size).subscribe({
      next: (res) => {
        this.productos = res.content;
        this.totalPages = res.totalPages;
        this.totalElements = res.totalElements;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar productos';
        this.loading = false;
      }
    });
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 0 && nuevaPagina < this.totalPages) {
      this.page = nuevaPagina;
      this.cargarProductos();
    }
  }

  cambiarEstado(id: number, activo: boolean) {
    if (confirm(`¿Estás seguro de ${activo ? 'activar' : 'desactivar'} este producto?`)) {
      this.productoService.cambiarEstadoActivo(id, activo).subscribe({
        next: () => this.cargarProductos(),
        error: () => alert('Error al cambiar de estado')
      });
    }
  }

  abrirAjuste(producto: Producto) {
    this.productoSeleccionado = producto;
    this.ajuste = 0;
    this.justificacion = '';
    this.ajusteModal = new bootstrap.Modal(document.getElementById('ajusteModal')!);
    this.ajusteModal.show();
  }

  guardarAjuste() {
    if (!this.productoSeleccionado) return;
    
    const dto: AjusteInventario = { ajuste: this.ajuste, justificacion: this.justificacion };
    this.productoService.ajustarInventario(this.productoSeleccionado.id!, dto).subscribe({
      next: () => {
        this.ajusteModal.hide();
        this.cargarProductos();
      },
      error: (err: any) => alert(err.error?.error || 'Error al ajustar inventario')
    });
  }
}
