export interface Producto {
  id?: number;
  nombre: string;
  marca?: string;
  categoria?: string;
  precio: number;
  existencias: number;
  activo?: boolean;
}

export interface AjusteInventario {
  ajuste: number;
  justificacion: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
