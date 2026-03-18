import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto, AjusteInventario, PaginatedResponse } from '../models/producto';

/**
 * Servicio central para la gestión de Productos.
 * <p>
 * Provee métodos estables para realizar las llamadas HTTP (GET, POST, PUT, PATCH)
 * hacia la API del backend usando Observables de RxJS.
 * </p>
 */
@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = 'http://localhost:8080/productos';

  constructor(private http: HttpClient) {}

  listarProductos(page: number, size: number): Observable<PaginatedResponse<Producto>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PaginatedResponse<Producto>>(this.apiUrl, { params });
  }

  obtenerProducto(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  crearProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  actualizarProducto(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  cambiarEstadoActivo(id: number, activo: boolean): Observable<Producto> {
    let params = new HttpParams().set('activo', activo.toString());
    return this.http.patch<Producto>(`${this.apiUrl}/${id}/activar`, {}, { params });
  }

  ajustarInventario(id: number, ajuste: AjusteInventario): Observable<Producto> {
    return this.http.post<Producto>(`${this.apiUrl}/${id}/ajustar`, ajuste);
  }
}
